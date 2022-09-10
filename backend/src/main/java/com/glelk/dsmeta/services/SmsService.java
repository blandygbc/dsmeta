package com.glelk.dsmeta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.glelk.dsmeta.entities.Sale;
import com.glelk.dsmeta.repositories.SaleRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    @Value("${twilio.sid}")
    private String twilioSid;

    @Value("${twilio.key}")
    private String twilioKey;

    @Value("${twilio.phone.from}")
    private String twilioPhoneFrom;

    @Value("${twilio.phone.to}")
    private String twilioPhoneTo;

    @Autowired
    private SaleRepository saleRepository;

    public void sendSms() {

        Twilio.init(twilioSid, twilioKey);

        PhoneNumber to = new PhoneNumber(twilioPhoneTo);
        PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

        Message message = Message.creator(to, from, "Teste").create();

        System.out.println(message.getSid());
    }

    public void sendSaleBySms(Long saleId) {

        Sale sale = saleRepository.findById(saleId).orElse(new Sale());
        StringBuilder stringBuilder = new StringBuilder();
        if (sale.getSellerName() != null) {
            stringBuilder.append("O(A) vendedor(a) ")
                    .append(sale.getSellerName())
                    .append(" foi destaque em ")
                    .append(sale.getDate().getMonthValue())
                    .append("/")
                    .append(sale.getDate().getYear())
                    .append(" com um total de R$ ")
                    .append(sale.getAmount());
        } else {
            stringBuilder.append("A venda não foi encontrada");
        }

        Twilio.init(twilioSid, twilioKey);

        PhoneNumber to = new PhoneNumber(twilioPhoneTo);
        PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

        Message message = Message.creator(to, from, stringBuilder.toString()).create();

        System.out.println(message.getSid());
    }
}
