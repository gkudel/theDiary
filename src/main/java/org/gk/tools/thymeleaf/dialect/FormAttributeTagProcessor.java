package org.gk.tools.thymeleaf.dialect;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.text.TextStringBuilder;
import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.AttributeValueQuotes;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class FormAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final String ATTR_NAME = "template";
	private static final int PRECEDENCE = 10000;
	private String editors;

	public FormAttributeTagProcessor(final String dialectPrefix, final String editors) {
		super(
				TemplateMode.HTML,
				dialectPrefix,
				null,
				false,
				ATTR_NAME,
				true,
				PRECEDENCE,
				true);
		this.editors = editors;
	}


	@Override
	protected void doProcess(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag,
							 AttributeName attributeName, String attributeValue,
							 IElementTagStructureHandler iElementTagStructureHandler) {
		try {
			final IEngineConfiguration configuration = iTemplateContext.getConfiguration();
			final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
			final IStandardExpression expression = parser.parseExpression(iTemplateContext, attributeValue);
			final Object dataModel = expression.execute(iTemplateContext);

			final IModelFactory modelFactory = iTemplateContext.getModelFactory();
			final IModel domModel = modelFactory.createModel();
			domModel.add(modelFactory.createOpenElementTag("form"));
			List<Field> fields = FieldUtils.getFieldsListWithAnnotation(dataModel.getClass(), org.gk.tools.thymeleaf.dialect.annotations.Field.class);
			BeanInfo info = null;
			info = Introspector.getBeanInfo(dataModel.getClass());
			if (CollectionUtils.isNotEmpty(fields) && info != null) {
				Map<String, PropertyDescriptor> properties = Arrays.stream(info.getPropertyDescriptors()).collect(Collectors.toMap(p -> p.getDisplayName(), p -> p));
				for (Field field : fields) {
					org.gk.tools.thymeleaf.dialect.annotations.Field annotatedFiled = field.getDeclaredAnnotation(org.gk.tools.thymeleaf.dialect.annotations.Field.class);
					PropertyDescriptor desc = properties.get(field.getName());
					if(desc == null) throw new RuntimeException("PropertyDescriptor desc is null");
					String editorName = annotatedFiled.name();
					if (StringUtils.isEmpty(editorName)) {
						editorName = field.getType().getSimpleName();
					}
					editorName = WordUtils.uncapitalize(editorName);
					String localVar = editorName;
					domModel.add(modelFactory.createOpenElementTag("div", new HashMap<String, String>() {{
						put("th:with", "value='" + desc.getReadMethod().invoke(dataModel, null) + "', id='"+field.getName()+"'");
					}}, AttributeValueQuotes.DOUBLE, false));
					domModel.add(modelFactory.createOpenElementTag("div", new HashMap<String, String>() {{
						put("th:replace", "~{" + editors + "/editors" +  " :: " + localVar + "}");
					}}, AttributeValueQuotes.DOUBLE, false));
					domModel.add(modelFactory.createCloseElementTag("div"));
					domModel.add(modelFactory.createCloseElementTag("div"));
				}
			}
			domModel.add(modelFactory.createCloseElementTag("form"));
			iElementTagStructureHandler.replaceWith(domModel, true);
		} catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}
}
