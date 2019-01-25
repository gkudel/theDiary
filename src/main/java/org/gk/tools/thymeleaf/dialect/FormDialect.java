package org.gk.tools.thymeleaf.dialect;

import org.apache.commons.lang3.Validate;
import org.gk.tools.thymeleaf.dialect.FormAttributeTagProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;


public class FormDialect extends AbstractProcessorDialect {
	private String editors;
	public FormDialect(final String dialectPrefix, final String editors ) {
		super(
				"Form Dialect",
				dialectPrefix,
				1000);
		Validate.notEmpty(editors, "editors");
		this.editors = editors;
	}

	@Override
	public Set<IProcessor> getProcessors(final String dialectPrefix) {

		final Set<IProcessor> processors = new HashSet<>();
		processors.add(new FormAttributeTagProcessor(dialectPrefix, editors));
		return processors;
	}
}
