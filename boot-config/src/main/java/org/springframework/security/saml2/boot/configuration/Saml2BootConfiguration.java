/*
 * Copyright 2002-2019 the original author or authors.
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

package org.springframework.security.saml2.boot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.saml2.registration.HostedSaml2IdentityProviderRegistration;
import org.springframework.security.saml2.registration.HostedSaml2ServiceProviderRegistration;
import org.springframework.security.saml2.registration.HostedSaml2Instance;

@ConfigurationProperties(prefix = "spring.security.saml2")
public class Saml2BootConfiguration {

	@NestedConfigurationProperty
	private LocalSaml2ServiceProviderConfiguration serviceProvider;

	@NestedConfigurationProperty
	private LocalSaml2IdentityProviderConfiguration identityProvider;

	public LocalSaml2ServiceProviderConfiguration getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(LocalSaml2ServiceProviderConfiguration serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public LocalSaml2IdentityProviderConfiguration getIdentityProvider() {
		return identityProvider;
	}

	public void setIdentityProvider(LocalSaml2IdentityProviderConfiguration identityProvider) {
		this.identityProvider = identityProvider;
	}

	public HostedSaml2Instance toSamlServerConfiguration() {
		return new HostedSaml2Instance(
			serviceProvider == null ? null : serviceProvider.toHostedServiceProviderRegistration(),
			identityProvider == null ? null : identityProvider.toHostedIdentityProviderRegistration()
		);
	}

	@Bean
	public HostedSaml2ServiceProviderRegistration samlServiceProviderRegistration() {
		return toSamlServerConfiguration().getServiceProvider();
	}

	@Bean
	public HostedSaml2IdentityProviderRegistration samlIdentityProviderRegistration() {
		return toSamlServerConfiguration().getIdentityProvider();
	}
}