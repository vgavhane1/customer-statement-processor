package com.rebobank.customer_statement_processor.converter;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.NamingConventions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatementDtoToStatementModel {
	
		@Autowired
		private ModelMapper modelMapper;

		@PostConstruct
		public void configure() {
			modelMapper.getConfiguration().setFieldMatchingEnabled(true)
					.setFieldAccessLevel(AccessLevel.PRIVATE)
					.setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
		}

		public <T> T convert(Object o, Class<T> t) {
			if (o == null) {
				return null;
			}
			return modelMapper.map(o, t);
		}

		
		public <T> Collection<T> convertList(Collection objects, Class<T> t) {
			if (objects == null) {
				return null;
			}
			return (Collection<T>) objects.stream().map(o -> modelMapper.map(o, t))
					.collect(Collectors.toList());
		}

		public <T> void createAndSetTypeMapping(Class<T> sourceBaseClass,
				Class<T> destBaseClass, Map<Class, Class> srcToDestSubclass) {
			TypeMap<T, T> typeMap = modelMapper.createTypeMap(sourceBaseClass,
					destBaseClass);
			srcToDestSubclass.forEach((k, v) -> typeMap.include(k, v));
		}

		public ModelMapper getModelMapper() {
			return modelMapper;
		}
}
