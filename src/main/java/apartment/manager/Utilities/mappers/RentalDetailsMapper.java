package apartment.manager.Utilities.mappers;

import apartment.manager.business.models.BaseRentalDetails;
import apartment.manager.presentation.models.RentalDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper()
public abstract class RentalDetailsMapper {

    public abstract RentalDetailsDto rentalDetailsToRentalDetailsDto(BaseRentalDetails rentalDetails);

    @Mapping(target = "paymentDueDates", ignore = true)
    public abstract void rentalDetailsDtoToRentalDetails(RentalDetailsDto rentalDetailsDto, @MappingTarget BaseRentalDetails rentalDetails);
}
