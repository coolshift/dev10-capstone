package learnb.yoga.controllers;

import learnb.yoga.domain.SessionService;
import learnb.yoga.models.Session;
import learnb.yoga.validation.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static learnb.yoga.controllers.ControllerHelper.getStatus;

@RestController
@RequestMapping("/session")
public class SessionController {

   private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @GetMapping("/count/{index}")
    public int getEnrolled(@PathVariable int index) {return service.getEnrolled(index);}

    @GetMapping("/{index}")
    public ResponseEntity<Session> findById(@PathVariable int index) {
        Session session = service.findById(index);
        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(session);
    }

    @GetMapping("/date/{date}")
public List<Session> findByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){

        return service.findByDate(date);
    }

    @PostMapping
    public ResponseEntity<Session> add(@RequestBody Session session){

        Result<Session> result = service.add(session);
        return new ResponseEntity<>(result.getPayload(), getStatus(result, HttpStatus.CREATED));
    }

    @PutMapping("/{index}")
    public ResponseEntity<Session> update(@PathVariable int index, @RequestBody Session session){

        if(session != null && session.getId() != index){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Session> result = service.update(session);
        return new ResponseEntity<>(getStatus(result, HttpStatus.NO_CONTENT));
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Void> delete(@PathVariable int index){
        Result<Session> result = service.deleteById(index);
        return  new ResponseEntity<>(getStatus(result,HttpStatus.NO_CONTENT));

    }

   /* private HttpStatus getStatus(Result<?> result, HttpStatus statusDefault) {
        switch (result.getStatus()) {
            case INVALID:
                return HttpStatus.PRECONDITION_FAILED;
            case DUPLICATE:
                return HttpStatus.FORBIDDEN;
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
        }
        return statusDefault;
    }*/

}
