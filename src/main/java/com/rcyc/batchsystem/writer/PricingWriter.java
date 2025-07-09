package com.rcyc.batchsystem.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.rcyc.batchsystem.model.elastic.Pricing;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import java.util.List;

@Component
public class PricingWriter implements ItemWriter<DefaultPayLoad<Pricing, Object, Pricing>> {
    @Override
    public void write(List<? extends DefaultPayLoad<Pricing, Object, Pricing>> items) throws Exception {
        for (DefaultPayLoad<Pricing, Object, Pricing> payload : items) {
            List<Pricing> pricings = (List<Pricing>) payload.getResponse();
            if (pricings != null) {
                pricings.forEach(pricing -> System.out.println(pricing.toString()));
            }
        }
    }
} 