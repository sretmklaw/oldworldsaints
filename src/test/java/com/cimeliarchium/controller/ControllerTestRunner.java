package com.cimeliarchium.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class ControllerTestRunner {

	private ResultActions actions;

	private ResultMatcher matcher;

	public ControllerTestRunner() { }

	public static class Builder {

		private MockMvc mockMvc;
		private String path;
		private RequestPostProcessor processor;
		private Map<String,String> params;
		private Map<String,Object> attributes;
		private String content;
		private ResultMatcher matcher;
		private MediaType mediaType;

		public Builder withMockMvc(MockMvc mockMvc) {
			this.mockMvc = mockMvc;
			return this;
		}

		public Builder withPath(String path) {
			this.path = path;
			return this;
		}

		public Builder withProcessor(RequestPostProcessor processor) {
			this.processor = processor;
			return this;
		}

		public Builder withParams(Map<String,String> params) {
			this.params = params;
			return this;
		}

		public Builder withAttributes(Map<String,Object> attributes) {
			this.attributes = attributes;
			return this;
		}

		public Builder withContent(String content) {
			this.content = content;
			return this;
		}

		public Builder withMatcher(ResultMatcher matcher) {
			this.matcher = matcher;
			return this;
		}

		public Builder withMediaType(MediaType mediaType) {
			this.mediaType = mediaType;
			return this;
		}

		public ControllerTestRunner doActions(ResultActions actions) {
			ControllerTestRunner ctra = new ControllerTestRunner();
			ctra.actions = actions;
			ctra.matcher = this.matcher;
			return ctra;
		}

		public ControllerTestRunner doGet() throws Exception {
			final MockHttpServletRequestBuilder requestBuilder = initRequest(get(path)).with(processor);
			if (this.mediaType != null) {
				requestBuilder.contentType(mediaType);
			}
			final ResultActions actions = this.mockMvc.perform(requestBuilder);
			return this.doActions(actions);
		}

		public ControllerTestRunner doPost() throws Exception {
			final ResultActions actions = this.mockMvc.perform(
					initRequest(post(path))
					.with(processor)
					.with(csrf())
					.contentType(mediaType)
					.content(content)
					.accept(mediaType));
			return this.doActions(actions);
		}

		private MockHttpServletRequestBuilder initRequest(MockHttpServletRequestBuilder req) {
			if (params != null && !params.isEmpty()) {
				for (Map.Entry<String,String> param : params.entrySet()) {
					req.param(param.getKey(), param.getValue());
				}
			};
			if (attributes != null && !attributes.isEmpty()) {
				for (Map.Entry<String,Object> attribute : attributes.entrySet()) {
					req.sessionAttr(attribute.getKey(), attribute.getValue());
				}
			};
			return req;
		}
	}

	public ControllerTestRunner andExpectUnauthenticatedOk() throws Exception {
		this.actions.andDo(print())
			.andExpect(unauthenticated())
			.andExpect(status().isOk())
			.andExpect(matcher);
		return this;
	}

	public ControllerTestRunner andExpectUnauthenticatedRedirect() throws Exception {
		this.actions.andDo(print())
			.andExpect(unauthenticated())
			.andExpect(status().isFound())
			.andExpect(matcher);
		return this;
	}

	public ControllerTestRunner andExpectAuthenticatedOk() throws Exception {
		this.actions.andDo(print())
			.andExpect(authenticated())
			.andExpect(status().isOk())
			.andExpect(matcher);
		return this;
	}

	public ControllerTestRunner andExpectAuthenticatedRedirect() throws Exception {
		this.actions.andDo(print())
			.andExpect(authenticated())
			.andExpect(status().isFound())
			.andExpect(matcher);
		return this;
	}

	public ControllerTestRunner andExpectAuthenticatedForbidden() throws Exception {
		this.actions.andDo(print())
			.andExpect(authenticated())
			.andExpect(status().isForbidden());
		return this;
	}

	public void andExpectModelContainsAttribute(String attributeName) throws Exception {
		this.actions.andExpect(model().attributeExists(attributeName));
	}
}
