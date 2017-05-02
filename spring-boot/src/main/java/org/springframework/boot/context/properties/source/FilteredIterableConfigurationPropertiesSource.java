/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.context.properties.source;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A filtered {@link IterableConfigurationPropertySource}.
 *
 * @author Phillip Webb
 * @author Madhura Bhave
 */
class FilteredIterableConfigurationPropertiesSource
		extends FilteredConfigurationPropertiesSource
		implements IterableConfigurationPropertySource {

	FilteredIterableConfigurationPropertiesSource(
			IterableConfigurationPropertySource source,
			Predicate<ConfigurationPropertyName> filter) {
		super(source, filter);
	}

	@Override
	public Stream<ConfigurationPropertyName> stream() {
		return StreamSupport.stream(getSource().spliterator(), false).filter(getFilter());
	}

	@Override
	protected IterableConfigurationPropertySource getSource() {
		return (IterableConfigurationPropertySource) super.getSource();
	}

	@Override
	public Optional<Boolean> containsDescendantOf(ConfigurationPropertyName name) {
		return Optional.of(stream().anyMatch(name::isAncestorOf));
	}

}
