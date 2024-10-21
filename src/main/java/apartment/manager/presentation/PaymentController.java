package apartment.manager.presentation;

import apartment.manager.business.PaymentService;
import apartment.manager.presentation.models.PaymentDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PaymentDto> createPayment(@RequestBody @Valid PaymentDto payment) {
        PaymentDto savedPayment = paymentService.createPayment(payment);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() // Builds URI based on the current request (i.e., /payments)
                .path("/{id}")         // Appends the ID of the created resource to the URI
                .buildAndExpand(savedPayment.getId()) // Replaces the placeholder with the new payment ID
                .toUri();
        return ResponseEntity.created(location).body(savedPayment);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentDto> getPayment(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(paymentService.getPaymentById(id));
    }

    @GetMapping(path = "/apartment-payments/{apartmentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PaymentDto>> getBuildingApartments(@PathVariable("apartmentId") long apartmentId) {
        return ResponseEntity.ok().body(paymentService.getPaymentsByApartmentId(apartmentId));
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentDto> updatePayment(@RequestBody @Valid PaymentDto PaymentDto, @PathVariable Long id) {
        return ResponseEntity.ok(paymentService.updatePayment(PaymentDto, id));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok("Payment {" + id + "} was deleted successfully");
    }
}
