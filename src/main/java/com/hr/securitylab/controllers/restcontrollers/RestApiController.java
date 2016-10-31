package com.hr.securitylab.controllers.restcontrollers;

import com.hr.securitylab.database.entities.rest.PollingRest;
import com.hr.securitylab.database.entities.rest.Response;
import com.hr.securitylab.services.EncryptionService;
import com.hr.securitylab.services.HttpService;
import com.hr.securitylab.services.PollingService;
import com.hr.securitylab.validation.ProductIdValidator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller which handles the api endpoints
 * Actions:
 *  - Turn vibrator on
 *  - Turn vibrator off
 *  - Set key -> retrieve the encryptionkey from the hibernate for the corresponding vibrator
 */

//TODO /off and /on should be POST
@RestController
@RequestMapping(value = "/api")
public class RestApiController {
    private HttpService http;
    private EncryptionService encryptionService;
    private PollingService pollingService;

    public RestApiController() {
        this.http = new HttpService();
        this.encryptionService = new EncryptionService();
        this.pollingService = new PollingService();
    }

    @RequestMapping(value = "/on", method = RequestMethod.GET)
    public String turnOn() throws Exception{
        return http.httpOn();
    }

    @RequestMapping(value = "/off", method = RequestMethod.GET)
    public String turnOff() throws Exception{
        return http.httpOff();
    }

    /**
     * Method for setting the symmetric encryption key based on the supplied productid
     * If the supplied key is not valid (contains characters or weird stuff), return bad response
     * @param request
     * @return
     */
    @RequestMapping(value = "/setkey", method = RequestMethod.POST)
    public Response retrieveDeviceEncryptionKey(HttpServletRequest request){
        if(ProductIdValidator.checkIfProductIdIsValid(request.getHeader("productid"))){
            return encryptionService.getKey(request);
        }
        else{
            return new Response(400, "Productid not valid");
        }
    }

    @RequestMapping(value = "/poll", method = RequestMethod.POST)
    public PollingRest checkPollingTable(HttpServletRequest request){
        if(ProductIdValidator.checkIfProductIdIsValid(request.getHeader("productid"))){
            return pollingService.checkPollingTable(request.getHeader("productid"));
        }
        return new PollingRest(null,false,"Productid is not valid");
    }

}
