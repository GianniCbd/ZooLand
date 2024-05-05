package natEst.nat.card;

import natEst.nat.exceptions.NotFoundException;
import natEst.nat.users.User;
import natEst.nat.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CardSRV {
    @Autowired
    private CardDAO cardDAO;
    @Autowired
    private UserRepository userDAO;

    public Page<Card> getAllCard(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return cardDAO.findAll(pageable);
    }

    public Card findById(UUID id) {
        return cardDAO.findById(id).orElseThrow(() -> new NotFoundException("la carta con id: " + id + " non è stato trovato"));
    }

    public Card save(UUID userId, CardDTO cardDTO) {
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException("user non trovato"));
        Card card = new Card(cardDTO.fullName(),  cardDTO.cardNumber(), cardDTO.expired(), cardDTO.cvv(),cardDTO.cardType(), user);
        return cardDAO.save(card);
    }

    public Page<Card> getUserCard(UUID userId, int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        Page<Card> cards = cardDAO.findAllByUserId(userId, pageable);
        if (cards.isEmpty()) {
            throw new NotFoundException("la lista carte è vuota");
        }
        return cards;
    }

    public Card updateCard( UUID id,CardDTO cardDTO){
        Card found = cardDAO.findById(id).orElseThrow(()-> new NotFoundException("Card not found with ID: " + id));
               found.setFullName(cardDTO.fullName());
                found.setCardNumber(cardDTO.cardNumber());
                found.setExpired(cardDTO.expired());
                found.setCvv(cardDTO.cvv());
                found.setCardType(cardDTO.cardType());
                return cardDAO.save(found);
    }


    public void delete(UUID cardId) {
        Card card = this.findById(cardId);
        cardDAO.delete(card);
    }
}