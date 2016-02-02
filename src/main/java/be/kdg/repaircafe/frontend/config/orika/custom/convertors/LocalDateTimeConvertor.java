package be.kdg.repaircafe.frontend.config.orika.custom.convertors;

import org.springframework.stereotype.Component;

@Component
public classOrikaLocalDateTimeConvertor extends BidirectionalConverter<LocalDateTime, LocalDateTime>
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/presentation-layer/orika/

    @Override
    public LocalDateTime convertTo(LocalDateTime source, Type<LocalDateTime> destinationType)
    {
        return (LocalDateTime.from(source));
    }

    @Override
    public LocalDateTime convertFrom(LocalDateTime source, Type<LocalDateTime> destinationType)
    {
        return (LocalDateTime.from(source));
    }
}
