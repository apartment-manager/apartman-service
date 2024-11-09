package apartment.manager.presentation;

import apartment.manager.business.ApartmentService;
import apartment.manager.presentation.models.ApartmentDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/apartments")
public class ApartmentController {
    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApartmentDto> createApartment(@RequestBody @Valid ApartmentDto apartment) {
        ApartmentDto savedApartment = apartmentService.createApartment(apartment);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() // Builds URI based on the current request (i.e., /apartments)
                .path("/{id}")
                .buildAndExpand(savedApartment.getId()) // Replaces the placeholder with the new apartment ID
                .toUri();
        return ResponseEntity.created(location).body(savedApartment);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApartmentDto> getApartment(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(apartmentService.getApartmentById(id));
    }

    @GetMapping(path = "/building-apartments/{buildingId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ApartmentDto>> getBuildingApartments(@PathVariable("buildingId") long buildingId) {
        return ResponseEntity.ok().body(apartmentService.getApartmentsByBuildingId(buildingId));
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApartmentDto> updateApartment(@RequestBody @Valid ApartmentDto apartmentDto, @PathVariable Long id) {
        return ResponseEntity.ok(apartmentService.updateApartment(apartmentDto, id));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteApartment(@PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.ok("Apartment {" + id + "} was deleted successfully");
    }

    @GetMapping(path = "/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ApartmentDto>> searchApartments(@RequestParam("query") String query) {
        return ResponseEntity.ok().body(apartmentService.searchApartments(query));
    }
}
