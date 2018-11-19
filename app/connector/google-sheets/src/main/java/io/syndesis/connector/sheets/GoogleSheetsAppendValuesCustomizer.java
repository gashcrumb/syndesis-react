/*
 * Copyright (C) 2016 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.syndesis.connector.sheets;

import com.google.api.services.sheets.v4.model.ValueRange;
import io.syndesis.integration.component.proxy.ComponentProxyComponent;
import io.syndesis.integration.component.proxy.ComponentProxyCustomizer;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.google.sheets.internal.GoogleSheetsApiCollection;
import org.apache.camel.component.google.sheets.internal.SheetsSpreadsheetsValuesApiMethod;
import org.apache.camel.util.ObjectHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class GoogleSheetsAppendValuesCustomizer implements ComponentProxyCustomizer {

    private String spreadsheetId;
    private String range;
    private String values;

    @Override
    public void customize(ComponentProxyComponent component, Map<String, Object> options) {
        setApiMethod(options);
        component.setBeforeProducer(this::beforeProducer);
    }

    private void setApiMethod(Map<String, Object> options) {
        spreadsheetId = (String) options.get("spreadsheetId");
        range = (String) options.get("range");
        values = (String) options.get("values");

        options.put("apiName",
                GoogleSheetsApiCollection.getCollection().getApiName(SheetsSpreadsheetsValuesApiMethod.class).getName());
        options.put("methodName", "append");
    }

    private void beforeProducer(Exchange exchange) {
        final Message in = exchange.getIn();
        final GoogleSheetsModel model = exchange.getIn().getBody(GoogleSheetsModel.class);

        if (model != null) {
            if (ObjectHelper.isNotEmpty(model.getValues())) {
                values = model.getValues();
            }
            if (ObjectHelper.isNotEmpty(model.getRange())) {
                range = model.getRange();
            }
            if (ObjectHelper.isNotEmpty(model.getSpreadsheetId())) {
                spreadsheetId = model.getSpreadsheetId();
            }
        }

        ValueRange valueRange = new ValueRange();
        valueRange.setValues(Collections.singletonList(Arrays.stream(values.split(","))
                                                             .collect(Collectors.toList())));

        in.setHeader("CamelGoogleSheets.spreadsheetId", spreadsheetId);
        in.setHeader("CamelGoogleSheets.range", range);
        in.setHeader("CamelGoogleSheets.values", valueRange);
        in.setHeader("CamelGoogleSheets.valueInputOption", "USER_ENTERED");
    }
}
