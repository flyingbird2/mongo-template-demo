package com.hongfei.qi.springmongodemo.convert;

import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;


public class MoneyReadConverter implements Converter<Document,Money> {
    @Override
    public Money convert(Document source) {
        Document document = (Document) source.get("money");
        String amount = document.getString("amount");
        double v = Double.parseDouble(amount);
        Document currency = (Document)document.get("currency");
        String currencyCode = currency.getString("code");
        return Money.of(CurrencyUnit.of(currencyCode),v);
    }
}
