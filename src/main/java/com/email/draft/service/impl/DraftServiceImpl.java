package com.email.draft.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import com.email.draft.repository.DraftRepository;
import com.email.draft.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.email.draft.model.Draft;
import com.email.draft.vo.DraftVO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DraftServiceImpl implements DraftService {

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	private String password;
	private String messagesUrl;

	@Autowired
	public DraftServiceImpl(String mailGunAPIMessagesUrl, String mailGunAPIUsername, String mailGunAPIPassword) {
		this.password = mailGunAPIPassword;
		this.messagesUrl = mailGunAPIMessagesUrl;
	}

	@Autowired
	private DraftRepository repository;

	public Draft save(long id, DraftVO draftVO, MultipartFile file) throws ParseException {
		Draft draft = new Draft();
		if (id > 0) {
			Optional<Draft> optionalDraft = repository.findById(id);
			draft = optionalDraft.get();
		}
		if (draftVO != null) {
			draft.setSubject(draftVO.getSubject() != null ? draftVO.getSubject() : "");
			draft.setDescription(draftVO.getDescription() != null ? draftVO.getDescription() : "");
			draft.setToEmail(draftVO.getToEmail() != null ? draftVO.getToEmail() : "");
			draft.setCc(draftVO.getCc() != null ? draftVO.getCc() : "");
			draft.setBc(draftVO.getBc() != null ? draftVO.getBc() : "");
			if (id > 0) {
				draft.setUpdated(new Date());
			} else {
				draft.setCreated(new Date());
			}
			if (!file.isEmpty()) {
				try {
				
			        File newFile = new File("attachements/"+file.getOriginalFilename());
			        newFile.createNewFile();
					draft.setFileName(file.getOriginalFilename());
					draft.setFileUrl("attachements/"+file.getOriginalFilename());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			repository.save(draft);
		}
		return draft;
	}

	public Draft deleteById(long id) {
		Optional<Draft> optionalDraft = repository.findById(id);
		if (optionalDraft == null) {
			return null;
		}
		Draft draft = optionalDraft.get();
		repository.delete(draft);
		return draft;
	}

	public DraftVO findById(long id) {
		DraftVO draftVO = new DraftVO();
		try {
			Optional<Draft> optionalDraft = repository.findById(id);
			if (optionalDraft != null) {
				Draft draft = optionalDraft.get();
				draftVO.setId(draft.getId());
				draftVO.setSubject(draft.getSubject() != null ? draft.getSubject() : "");
				draftVO.setDescription(draft.getDescription() != null ? draft.getDescription() : "");
				draftVO.setToEmail(draft.getToEmail() != null ? draft.getToEmail() : "");
				draftVO.setCc(draft.getCc() != null ? draft.getCc() : "");
				draftVO.setBc(draft.getBc() != null ? draft.getBc() : "");
				draftVO.setFileName(draft.getFileName() != null ? draft.getFileName() : "");
				draftVO.setFileUrl(draft.getFileUrl() != null ? draft.getFileUrl() : "");
			}
		} catch (Exception ex) {
			return draftVO;
		}
		return draftVO;
	}

	@Override
	public JsonNode sendMail(long id, String from) throws Exception {
		DraftVO draft = findById(id);
		HttpResponse<JsonNode> request = Unirest.post(
				  messagesUrl)
				 .basicAuth("api", password)
				 .queryString("from", from)
				 .queryString("to", draft.getToEmail())
				 .queryString("cc", draft.getCc())
				 .queryString("bcc",draft.getBc())
				 .queryString("subject", draft.getSubject())
				 .queryString("text", draft.getDescription())
				 .field("attachment",  new File("attachements/"+draft.getFileName()))
				 .asJson();
		System.out.println("status: "+request.getStatus());
		if(request.getStatus() == 200) {
			deleteById(draft.getId());
			return request.getBody();
		}else {
			throw new Exception(request.getStatusText());
		}
		
	}

}