/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.springframework.security.saml.provider.identity.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.saml.provider.config.AbstractProviderSecurityConfiguration;
import org.springframework.security.saml.provider.identity.IdentityProviderService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static org.springframework.security.saml.util.StringUtils.stripSlashes;

public abstract class SamlIdentityProviderSecurityConfiguration
	extends AbstractProviderSecurityConfiguration<IdentityProviderService> {

	private static Log logger = LogFactory.getLog(SamlIdentityProviderSecurityConfiguration.class);

	private final SamlIdentityProviderServerBeanConfiguration configuration;

	public SamlIdentityProviderSecurityConfiguration(SamlIdentityProviderServerBeanConfiguration configuration) {
		this("saml/idp/", configuration);
	}

	public SamlIdentityProviderSecurityConfiguration(String prefix,
													 SamlIdentityProviderServerBeanConfiguration configuration) {
		super(stripSlashes(prefix+"/"));
		this.configuration = configuration;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String filterChainPattern = "/" + stripSlashes(getPrefix()) + "/**";
		logger.info("Configuring SAML IDP on patther:"+filterChainPattern);
		http
			.antMatcher(filterChainPattern)
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/metadata").permitAll()
			.antMatchers("/**").authenticated()
			.and();
	}

	public SamlIdentityProviderServerBeanConfiguration getConfiguration() {
		return configuration;
	}
}
