package natEst.nat.tickets;

import natEst.nat.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketCTRL {

    @Autowired
    TicketSrv ticketSrv;

    @GetMapping("/all")
    public Page<Ticket> getTicket(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id")String orderBy){
        return this.ticketSrv.getTicket(page,size,orderBy);
    }

    @GetMapping("/ticket-user")
    public Page<Ticket> getAllTickets(@RequestParam UUID userId,@RequestParam(defaultValue = "0")int page,
                                      @RequestParam(defaultValue = "10")int size,
                                      @RequestParam(defaultValue = "id")String orderBy)
    {
        return this.ticketSrv.getUserTickets(userId,page,size,orderBy);
    }

    @GetMapping("/myTicket")
    public List<Ticket>getMyTicket(@RequestParam UUID userId){
        return  this.ticketSrv.getTicketsByUserId(userId);
    }

    @GetMapping("/{id}")
    public Ticket findById(@PathVariable long id){
        return this.ticketSrv.findById(id);
    }

//    @PostMapping("/save")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Ticket save(@AuthenticationPrincipal User currentAuthenticatedUser,@RequestBody TicketDTO ticketDTO){
//        return this.ticketSrv.saveTicketUser(currentAuthenticatedUser.getId(),ticketDTO);
//    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TicketDTO> save(@RequestBody TicketDTO ticketDTO, @AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        TicketDTO savedTicket = ticketSrv.save(ticketDTO, user);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody TicketDTO ticketDTO){
        return ticketSrv.updateTicket(id,ticketDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketSrv.delete(id);
    }




}
