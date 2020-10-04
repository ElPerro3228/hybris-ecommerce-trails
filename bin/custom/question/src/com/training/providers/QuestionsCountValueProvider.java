package com.training.providers;

import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CommerceClassificationPropertyValueProvider;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.ProductReviewAverageRatingValueProvider;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.ModelPropertyFieldValueProvider;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QuestionsCountValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable {
    private static final Logger LOG = Logger.getLogger(QuestionsCountValueProvider.class);
    private FieldNameProvider fieldNameProvider;
    @Override
    public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
                                                 final Object obj) throws FieldValueProviderException
    {
        if (obj instanceof ProductModel) {
            final ProductModel product = (ProductModel) obj;
            final List<FieldValue> fieldValues = createFieldValue(product, indexConfig, indexedProperty);
            return fieldValues;
        }
        else {
            throw new FieldValueProviderException("Cannot get questions count");
        }
    }

    private List<FieldValue> createFieldValue(ProductModel product, IndexConfig indexConfig, IndexedProperty indexedProperty) {
        final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
        final Integer questionsCount = product.getQuestionsCount();
        if (questionsCount != null) {
            addFieldValues(fieldValues, indexedProperty, null, questionsCount);
        }
        return fieldValues;
    }

    private void addFieldValues(List<FieldValue> fieldValues, IndexedProperty indexedProperty, final LanguageModel language, Integer questionsCount) {
        List<String> rangeNameList = null;
        try {
            rangeNameList = getRangeNameList(indexedProperty, questionsCount, "QCR");
        }
        catch (final FieldValueProviderException e) {
            LOG.error("Could not get Range value", e);
        }
        String rangeName = null;
        if (CollectionUtils.isNotEmpty(rangeNameList)) {
            rangeName = rangeNameList.get(0);
        }
        final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, (language == null) ? null : language.getIsocode());
        final Object valueToPass = (rangeName == null ? questionsCount : rangeName);
        for (final String fieldName : fieldNames) {
            fieldValues.add(new FieldValue(fieldName, valueToPass));
        }
    }

    public FieldNameProvider getFieldNameProvider() {
        return fieldNameProvider;
    }

    @Required
    public void setFieldNameProvider(FieldNameProvider fieldNameProvider) {
        this.fieldNameProvider = fieldNameProvider;
    }
}
