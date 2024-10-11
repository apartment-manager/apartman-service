package apartment.manager.presentation;

import apartment.manager.business.BuildingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/buildings")
public class BuildingController {
    private BuildingService buildingService;

    public BuildingController(BuildingService buildingService){
        this.buildingService=buildingService;
    }

    @PostMapping(path="/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBuilding(@RequestBody String name, @RequestBody String address){
        buildingService.createBuilding(name, address);
    }
}
