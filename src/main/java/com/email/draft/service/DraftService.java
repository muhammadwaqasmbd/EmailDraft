package com.email.draft.service;

import java.io.IOException;
import java.text.ParseException;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;
import com.email.draft.model.Draft;
import com.email.draft.vo.DraftVO;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.web.multipart.MultipartFile;

@Service
public interface DraftService {

  public Draft save(long id, DraftVO draftVO, MultipartFile file) throws ParseException;

  public Draft deleteById(long id);

  public DraftVO findById(long id);
  
  public JsonNode sendMail(long id, String from) throws UnirestException, Exception;
  
}