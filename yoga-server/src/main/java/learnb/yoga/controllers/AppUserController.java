package learnb.yoga.controllers;

import learnb.yoga.domain.ActionStatus;
import learnb.yoga.models.AppUser;
import learnb.yoga.models.Location;
import learnb.yoga.security.AppUserService;
import learnb.yoga.validation.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AppUserController {

    private final AppUserService service;

    public AppUserController(AppUserService service) {
        this.service = service;
    }

    @GetMapping("/{index}")
    public ResponseEntity<AppUser> findById(@PathVariable int index){

        AppUser appUser = service.findById(index);
        if(appUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appUser,HttpStatus.OK);

    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AppUser> findByEmail(@PathVariable String email){

        AppUser appUser = service.findByEmail(email);
        if(appUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appUser,HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<?> add(@RequestBody AppUser appUser){
//
//        if(appUser.getAppUserId() != 0){
//
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        Result<Location> result = service.add(appUser);
//        if(!result.isSuccess()){
//            return new ResponseEntity<>(result.getMessages(),HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<>(result.getPayload(),HttpStatus.CREATED);
//
//    }

@PutMapping("/{index}")
public ResponseEntity<?> update(@PathVariable int index, @RequestBody AppUser appUser) {

    if(index != appUser.getAppUserId()){
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    if(appUser.getAppUserId() <= 0){

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Result<AppUser> result= service.update(appUser);
    if (!result.isSuccess()) {
        if (result.getStatus() == ActionStatus.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(result.getPayload(), HttpStatus.NO_CONTENT);

}


}
