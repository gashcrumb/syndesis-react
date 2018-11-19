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
package org.apache.camel.component.google.sheets.stream;

import com.google.api.services.sheets.v4.model.ValueRange;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.apache.camel.component.google.sheets.stream.GoogleSheetsStreamConstants.MAJOR_DIMENSION;
import static org.apache.camel.component.google.sheets.stream.GoogleSheetsStreamConstants.RANGE;
import static org.apache.camel.component.google.sheets.stream.GoogleSheetsStreamConstants.SPREADSHEET_ID;

public class SheetsStreamConsumerIntegrationTest extends AbstractGoogleSheetsStreamTestSupport {

    private String spreadsheetId = "1Um7NfoWCX8vG1Tc6I0WYoiYM3skHflTY430S5FXn7UQ";
    private String range = "A1:B2";
    
    @Ignore
    @Test
    @SuppressWarnings("unchecked")
    public void testConsumeValueRange() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        assertMockEndpointsSatisfied();
        
        Exchange exchange = mock.getReceivedExchanges().get(0);
        Assert.assertTrue(exchange.getIn().getHeaders().containsKey(SPREADSHEET_ID));
        Assert.assertTrue(exchange.getIn().getHeaders().containsKey(RANGE));
        Assert.assertTrue(exchange.getIn().getHeaders().containsKey(MAJOR_DIMENSION));
        Assert.assertEquals(spreadsheetId, exchange.getIn().getHeaders().get(SPREADSHEET_ID));
        Assert.assertEquals("Sheet1!" + range, exchange.getIn().getHeaders().get(RANGE));
        Assert.assertEquals("ROWS", exchange.getIn().getHeaders().get(MAJOR_DIMENSION));

        ValueRange values = (ValueRange) exchange.getIn().getBody();
        Assert.assertEquals(2L, values.getValues().size());
        Assert.assertEquals("a1", values.getValues().get(0).get(0));
        Assert.assertEquals("b1", values.getValues().get(0).get(1));
        Assert.assertEquals("a2", values.getValues().get(1).get(0));
        Assert.assertEquals("b2", values.getValues().get(1).get(1));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("google-sheets-stream://data?spreadsheetId=" + spreadsheetId + "&range=" + range + "&delay=5000&maxResults=5").to("mock:result");
            }
        };
    }
}
