package com.example.CarRepair.Controller;

import com.example.CarRepair.Domain.User;
import com.example.CarRepair.Model.NewUserForm;
import com.example.CarRepair.Services.UpdateUserImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UpdateUserController {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(NewUserController.class);
    //h forma pou exei auto to id sto ftl toy updateUser
    private static final String UPDATE_USER_FORM = "updateForm";
    private static final String UPDATED_READY_USER_FORM = "updatedReadyUserForm";


    //bean toy service
    @Autowired
    UpdateUserImpl updateService;


    //methodos gia na ferw ta dedomena toy xrhsth(prin ginei update) mprosta sto view
    @RequestMapping(value = "/admin/updateUser/{userID}", method = RequestMethod.GET)
    public String getUserData(@PathVariable Long userID, Model model)

    {
        NewUserForm userForm = updateService.findUser(userID);
        //ginetai binding metaksu ftl kai ths formas pou tha parw kalwntas ayth th methodo apo to service
        model.addAttribute(UPDATE_USER_FORM, userForm);
        //epistrefei to ftl me ta data ths formas
        return "admin/updateUser"; /*redirect:/UpdateUser*/

    }





    //epistrefei mia ananeomenh forma me ta updated stoixeia toy xrhsth

    @RequestMapping(value = "/admin/updateUser/{userID}", method = RequestMethod.POST)
    public String UpdatedUser(@Valid @ModelAttribute(UPDATED_READY_USER_FORM)
                               NewUserForm userForm, BindingResult bindingResult, HttpSession session,
                              RedirectAttributes redirectAttributes, @PathVariable Long userID, Model model)
    {

        if (bindingResult.hasErrors())
        {
            //error handling
            logger.error(String.format("%s Validation Errors present: ", bindingResult.getErrorCount()));
            return "/admin/updateUser/{userID}";
        }

        try
        {

            User updatedUser = updateService.updateUserById(userID, userForm);
            model.addAttribute(UPDATED_READY_USER_FORM, userForm);
        }
        catch (Exception exception)
        {

            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            logger.error("User update failed: " + exception);
            return "redirect:/admin/updateUser/{userID}";

        }

        redirectAttributes.addFlashAttribute("message", "You have sucessfully completed update functionality");
        return "redirect:admin/admH";

    }


}







//
//        @RequestMapping(value = "/admin/updatedReadyUser/{userID}", method = RequestMethod.POST)
//        public String UpdatedUser(@PathVariable Long userID,@PathVariable NewUserForm userForm, Model model)
//        {
//
//            NewUserForm updatedUser = updateService.patchBookById(userID, userForm);
//            model.addAttribute( UPDATED_READY_USER_FORM,updatedUser);
//            return "admin/updatedReadyUser";
//        }





//    @RequestMapping(value = "/admin/updateUser/{userID}", method = RequestMethod.POST)
//    public ResponseEntity<NewUserForm> patchUser(@PathVariable("userID") Long id, @RequestBody NewUserForm userForm) {
//        try {
//            NewUserForm updatedUser = updateService.patchBookById(id, userForm);
//            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//        } catch (Exception ex) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//

