package learnb.yoga.domain;

import learnb.yoga.data.ReservationRepository;
import learnb.yoga.data.SessionRepository;
import learnb.yoga.models.AppUser;
import learnb.yoga.models.Reservation;
import learnb.yoga.models.Session;
import learnb.yoga.validation.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {


    final ReservationRepository repository;

    final SessionRepository sessionRepository;

    public ReservationService(ReservationRepository repository, SessionRepository sessionRepository) {
        this.repository = repository;
        this.sessionRepository = sessionRepository;
    }


    public Reservation findById(int id) {
        return repository.findById(id);
    }

    public List<Reservation> findByStudent(AppUser student){
        return repository.findByStudent(student);
    }

    public Result<Reservation> add(Reservation reservation) {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        if (reservation.getId() != 0) {
            result.addMessage(ActionStatus.INVALID, "reservation id cannot be set for add operation");
            return result;
        }

        Reservation inserted = repository.add(reservation);
        if (inserted == null) {
            result.addMessage(ActionStatus.INVALID, "insert failed");
        } else {
            result.setPayload(inserted);
        }
        return result;
    }
    public Result<Reservation> update(Reservation reservation){
     Result<Reservation> result = validate(reservation);
     if(!result.isSuccess()){
         return result;
     }
     if(reservation.getId() <= 0 ){
         result.addMessage(ActionStatus.INVALID, "resrvation id must be set for update operation");
         return result;
     }
     if(!repository.update(reservation)) {
         result.addMessage(ActionStatus.NOT_FOUND, "reservation id `" + reservation.getId() + "` not found.");
     }

     return result;

    }
    public Result<Reservation> deleteById(int id){
        Result<Reservation> result = new Result<>();
        boolean deleted = repository.deleteById(id);
        if(!deleted) {
            result.addMessage(ActionStatus.NOT_FOUND, "reservation id " + id + " not found.");
        }
        return result;
    }

    private Result<Reservation> validate(Reservation reservation){

        Result<Reservation> result = new Result<>();

        if(reservation == null){
            result.addMessage(ActionStatus.INVALID, "Reservation cannot be null.");
            return result;
        }
        if(reservation.getSession()==null){
            result.addMessage(ActionStatus.INVALID, "Session cannot be null.");
            return result;
        }
        if(reservation.getStudent()==null){
            result.addMessage(ActionStatus.INVALID, "Student cannot be null.");
            return result;
        }

        if(sessionRepository.getEnrollmentCount(reservation.getSession().getId())
                >= reservation.getSession().getCapacity()){
            result.addMessage(ActionStatus.INVALID, "Class is full");
            return result;
        }
        List<Reservation> studentReservations =
                repository.findByStudent(reservation.getStudent());

        for(Reservation sr : studentReservations){
            boolean overlaps = sr.getSession().getStart()
                    .isBefore(reservation.getSession().getEnd())
                    && sr.getSession().getEnd()
                    .isAfter(reservation.getSession().getStart());
            if(sr.getId() != reservation.getId() && overlaps){
                result.addMessage(ActionStatus.INVALID, "student has conflicting reservation");
                return result;
            }

        }
        return result;
    }


}
