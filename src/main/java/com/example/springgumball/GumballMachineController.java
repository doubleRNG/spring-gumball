package com.example.springgumball;

import com.example.gumballmachine.GumballMachine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.InetAddress;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;

@Slf4j
@Controller
@RequestMapping("/")
public class GumballMachineController {

    private static String KEY = "kwRg54x2Go9iEdl49jFENRM12Mp711QI" ;
    private static String MODEL_NUMBER = "SB102927" ;
    private static String SERIAL_NUMBER = "2134998871109" ;

    private GumballModel g ;

    private static String hmac_sha256(String secretKey, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256") ;
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256") ;
            mac.init(secretKeySpec) ;
            Encoder encoder = java.util.Base64.getEncoder() ;
            byte[] digest = mac.doFinal(data.getBytes()) ;
            String hash = encoder.encodeToString(digest) ;
            return hash ;
        } catch (InvalidKeyException e1) {
            throw new RuntimeException("Invalid key exception while converting to HMAC SHA256") ;
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("Java Exception Initializing HMAC Crypto Algorithm") ;
        }
    }

    @GetMapping
    public String getAction( @ModelAttribute("command") GumballCommand command,
                             Model model) {

        if ( g == null ) {
            g = new GumballModel();
            g.setModelNumber( MODEL_NUMBER ) ;
            g.setSerialNumber( SERIAL_NUMBER ) ;
            g.setCountGumballs( 10 ) ;
        }

        GumballMachine gm = new GumballMachine() ;
        gm.setModelNumber( g.getModelNumber() ) ;
        gm.setSerialNumber( g.getSerialNumber() ) ;
        String message = gm.toString() ;

        String state = gm.getState().getClass().getName() ;
        command.setState( state ) ;

        String ts = String.valueOf(java.lang.System.currentTimeMillis()) ;
        command.setTimestamp( ts ) ;

        String text = state + "/" + ts ;
        String hash = hmac_sha256( KEY, text ) ;
        command.setHash( hash ) ;

        String server_ip = "" ;
        String host_name = "" ;
        try {
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;

        } catch (Exception e) { }

        model.addAttribute( "hash", hash ) ;
        model.addAttribute( "message", message ) ;

        model.addAttribute( "server",  host_name + "/" + server_ip ) ;
        return "gumball" ;

    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") GumballCommand command,
                             @RequestParam(value="action", required=true) String action,
                             Errors errors, Model model) {

        log.info( "Action: " + action ) ;
        log.info( "Command: " + command ) ;

        if ( g == null ) {
            g = new GumballModel();
            g.setModelNumber( MODEL_NUMBER ) ;
            g.setSerialNumber( SERIAL_NUMBER ) ;
            g.setCountGumballs( 10 ) ;
        }

        String input_hash = command.getHash() ;
        String input_state = command.getState() ;
        String input_timestamp = command.getTimestamp() ;

        String input_text = input_state + "/" + input_timestamp ;
        String calculated_hash = hmac_sha256( KEY, input_text ) ;

        log.info( "Input Hash: " + input_hash ) ;
        log.info( "Valid Hash: " + calculated_hash ) ;

        if ( !input_hash.equals(calculated_hash) ) {
            throw new GumballServerError() ;
        }

        long ts1 = Long.parseLong( input_timestamp ) ;
        long ts2 = java.lang.System.currentTimeMillis() ;
        long diff = ts2 - ts1 ;

        log.info( "Input Timestamp: " + String.valueOf(ts1) ) ;
        log.info( "Current Timestamp: " + String.valueOf(ts2) ) ;
        log.info( "Timestamp Delta: " + String.valueOf(diff) ) ;

        if ( (diff/1000) > 50 ) {
            throw new GumballServerError() ;
        }

        GumballMachine gm = new GumballMachine() ;
        gm.setModelNumber( g.getModelNumber() ) ;
        gm.setSerialNumber( g.getSerialNumber() ) ;
        gm.setState( input_state ) ;

        if ( action.equals("Insert Quarter") ) {
            gm.insertQuarter() ;
        }

        if ( action.equals("Turn Crank") ) {
            command.setMessage("") ;
            gm.turnCrank() ;
        }

        String message = gm.toString() ;

        String state = gm.getState().getClass().getName() ;
        command.setState( state ) ;

        String ts = String.valueOf(java.lang.System.currentTimeMillis()) ;
        command.setTimestamp( ts ) ;

        String text = state + "/" + ts ;
        String hash = hmac_sha256( KEY, text ) ;
        command.setHash( hash ) ;

        String server_ip = "" ;
        String host_name = "" ;
        try {
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;

        } catch (Exception e) { }

        model.addAttribute( "hash", hash ) ;
        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

        if (errors.hasErrors()) {
            return "gumball";
        }

        return "gumball";
    }

}