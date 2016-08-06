package me.gking2224.model.execution;

import org.springframework.stereotype.Component;

@Component
public class XferRatesStaticDataProvider extends AbstractStaticDataProvider {

    private static final String IDENTIFIER = "me.gking2224.model.execution.staticdata.xferrates";

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }
    

    public Double getRate(String fromCurrency, String toCurrency) {
        return 1.978d;
    }
    

}
