package uz.pdp.aloqakampaniyasi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.aloqakampaniyasi.entity.Employee;
import uz.pdp.aloqakampaniyasi.entity.TourniquetCard;
import uz.pdp.aloqakampaniyasi.entity.TourniquetHistory;
import uz.pdp.aloqakampaniyasi.payload.ApiResponse;
import uz.pdp.aloqakampaniyasi.payload.TourniquetCardDto;
import uz.pdp.aloqakampaniyasi.payload.TourniquetHistoryDto;
import uz.pdp.aloqakampaniyasi.repository.EmployeeRepository;
import uz.pdp.aloqakampaniyasi.repository.TourniquetHistoryRepository;
import uz.pdp.aloqakampaniyasi.repository.TourniquetRepository;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class TourniquetService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TourniquetRepository tourniquetRepository;
    @Autowired
    TourniquetHistoryRepository tourniquetHistoryRepository;

    public ApiResponse create(TourniquetCardDto dto) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(dto.getEmployeeEmail());
        if (!optionalEmployee.isPresent()) {
            return new ApiResponse("Employee not found", false);
        }
        Employee employee = optionalEmployee.get();
        TourniquetCard card = new TourniquetCard();
        card.setStatus(true);
        card.setEmployee(employee);
        tourniquetRepository.save(card);
        return new ApiResponse("Tourniquet card is successfully created", true);
    }


    public ApiResponse edit(TourniquetCardDto dto, String email) {
         Optional<Employee> optionalEmployee = employeeRepository.findByEmail(dto.getEmployeeEmail());
        if (!optionalEmployee.isPresent()) {
            return new ApiResponse("Employee not found", false);
        }
        Optional<TourniquetCard> optionalTourniquetCard = tourniquetRepository.findByEmployee_EmailAndStatusTrue(email);
        if (optionalTourniquetCard.isPresent()) {
            TourniquetCard card = optionalTourniquetCard.get();
            card.setEmployee(optionalEmployee.get());
            tourniquetRepository.save(card);
            return new ApiResponse("Card update", true);
        }
        return new ApiResponse("Card topilmadi", false);
    }

    public ApiResponse activate(TourniquetHistoryDto dto) {
        Optional<TourniquetCard> optionalTourniquetCard =
                tourniquetRepository.findById(Integer.valueOf(dto.getCardId()));
        if (optionalTourniquetCard.isPresent()) {
            TourniquetCard card = optionalTourniquetCard.get();
            card.setStatus(true);
            card.setExpireDate(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365 * 10));
            tourniquetRepository.save(card);
            return new ApiResponse("Card active", true);
        }
        return new ApiResponse("Card topilmadi", true);
    }

    public ApiResponse enter(TourniquetHistoryDto dto) {
        Optional<TourniquetCard> cardOptional = tourniquetRepository.findById(Integer.valueOf(dto.getCardId()));
        if (!cardOptional.isPresent()) {
            return new ApiResponse("Card not found", false);
        }
        TourniquetCard card = tourniquetRepository.save(checkActive(cardOptional.get()));
        if (card.isStatus()) {
            TourniquetHistory tourniquetHistory = new TourniquetHistory();
            tourniquetHistory.setEnteredAt(Timestamp.valueOf(LocalDateTime.now()));
            tourniquetHistory.setTourniquetCard(card);
            tourniquetHistoryRepository.save(tourniquetHistory);
            return new ApiResponse("Entered", true);
        }
        return new ApiResponse("Expiration date of the card", false);
    }

    public TourniquetCard checkActive(TourniquetCard card) {
        if (card.getExpireDate().before(new Date())) {
            card.setStatus(false);
        }
        return card;
    }


    public ApiResponse exit(TourniquetHistoryDto dto) {
        Optional<TourniquetCard> cardOptional = tourniquetRepository.findById(Integer.valueOf(dto.getCardId()));
        if (!cardOptional.isPresent()) {
            return new ApiResponse("Card not found", false);
        }
        TourniquetCard card = tourniquetRepository.save(checkActive(cardOptional.get()));
        if (card.isStatus()) {
            TourniquetHistory tourniquetHistory = new TourniquetHistory();
            tourniquetHistory.setExitedAt(Timestamp.valueOf(LocalDateTime.now()));
            tourniquetHistory.setTourniquetCard(card);
            tourniquetHistoryRepository.save(tourniquetHistory);
            return new ApiResponse("Exited", true);
        }
        return new ApiResponse("", false);
    }

    public ApiResponse delete(String id) {
        Optional<TourniquetCard> optionalTourniquetCard =
                tourniquetRepository.findById(Integer.valueOf(id));
        if (!optionalTourniquetCard.isPresent()) {
            return new ApiResponse("Card not found", true);
        }
        TourniquetCard card = optionalTourniquetCard.get();
        card.setStatus(false);
        tourniquetRepository.save(card);
        return new ApiResponse("Card deleted", true);
    }
}
