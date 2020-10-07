package com.email.draft.controller;

import java.text.ParseException;

import javax.mail.MessagingException;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.email.draft.model.Draft;
import com.email.draft.service.DraftService;
import com.email.draft.vo.DraftVO;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
public class DraftController {

	@Autowired
	private DraftService service;

	@GetMapping("/email/draft/{id}")
	public ResponseEntity<DraftVO> getDraft(@PathVariable long id) {
		DraftVO draft = service.findById(id);
		if(draft!=null) {
			return new ResponseEntity<DraftVO>(draft, HttpStatus.OK);
		}else {
			return new ResponseEntity<DraftVO>(new DraftVO(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/email/draft/{id}")
	public ResponseEntity<Void> deleteCandidate(@PathVariable long id) {
		Draft draft = service.deleteById(id);
		if (draft != null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping(value = "/email/draft/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Draft> updateCandidate(@PathVariable long id,
													   @RequestParam String subject, @RequestParam String description, @RequestParam String toEmail, @RequestParam String toCc,
													   @RequestParam String toBc, @RequestParam MultipartFile file) {
		Draft draft = new Draft();
		DraftVO draftVO = new DraftVO();
		try {
			draftVO.setSubject(StringUtils.isEmpty(subject) ? "" : subject);
			draftVO.setDescription(StringUtils.isEmpty(description) ? "" : description);
			draftVO.setToEmail(StringUtils.isEmpty(toEmail) ? "" : toEmail);
			draftVO.setCc(StringUtils.isEmpty(toCc) ? "" : toCc);
			draftVO.setBc(StringUtils.isEmpty(toBc) ? "" : toBc);
			draft = service.save(id, draftVO, file);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Draft>(draft, HttpStatus.OK);
	}

	@PostMapping(value = "/email/draft", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Draft> createCandidate(@RequestParam String subject, @RequestParam String description, @RequestParam String toEmail, @RequestParam String toCc,
			   @RequestParam String toBc, @RequestParam MultipartFile file){
		Draft draft = new Draft();
		DraftVO draftVO = new DraftVO();
		try {
			draftVO.setSubject(StringUtils.isEmpty(subject) ? "" : subject);
			draftVO.setDescription(StringUtils.isEmpty(description) ? "" : description);
			draftVO.setToEmail(StringUtils.isEmpty(toEmail) ? "" : toEmail);
			draftVO.setCc(StringUtils.isEmpty(toCc) ? "" : toCc);
			draftVO.setBc(StringUtils.isEmpty(toBc) ? "" : toBc);
			draft = service.save(0, draftVO, file);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Draft>(draft, HttpStatus.OK);
	}
	
	@PostMapping(value = "/email/send/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity sendmail(@PathVariable("id") Long id, @RequestParam String from) throws UnirestException {
		try {
			JsonNode response = service.sendMail(id, from);
			System.out.println("response: "+response);
			return ResponseEntity.ok("sent");
		}catch(Exception ex) {
			return ResponseEntity.noContent().build();

		}
    }

}