package com.defers.mypastebin.domain.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class PasteIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor,
                                 Object o) {
        // TODO
        var rnd = new Random();
        return String.valueOf(rnd.nextLong());
    }
}
