package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.entities.InternalMail;
import com.grpc.grpcServer.port.in.rest.dto.InternalMailDto;
import com.grpc.grpcServer.port.in.rest.dto.Reply;
import com.grpc.grpcServer.port.in.rest.mapper.InternalMailMapper;
import com.grpc.grpcServer.repositories.InternalMailRepository;
import com.grpc.grpcServer.service.InternalMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternalMailServiceImpl implements InternalMailService {

    @Autowired
    InternalMailRepository internalMailRepository;

    @Autowired
    InternalMailMapper internalMailMapper;

    @Override
    public boolean newInternalMail(InternalMailDto request) throws Exception {
        InternalMail internalMail = internalMailMapper.dtoTointernalMailNew(request);
        if(internalMailRepository.findByMail(request.getDestination(), request.getSource(), request.getSubject()).isPresent())throw new Exception("Ya existe un mail para ese asunto");
        if (internalMail == null && internalMail.getSubjectReply() != null) throw new Exception("Los datos ingresados no son correctos. Intente nuevamente");
        internalMailRepository.save(internalMail);
        return true;
    }

    @Override
    public List<InternalMailDto> findByDestination(String destination) throws Exception {
        List<InternalMail> internalMailList = internalMailRepository.findByDestination(destination);
        List<InternalMailDto> internalMailDto = internalMailMapper.InternalMailListToDto(internalMailList);
        return internalMailDto;
    }

    @Override
    public List<InternalMailDto> findBySource(String source) throws Exception {
        List<InternalMail> internalMailList = internalMailRepository.findBySource(source);
        List<InternalMailDto> internalMailDto = internalMailMapper.InternalMailListToDto(internalMailList);
        return internalMailDto;
    }

    @Override
    public InternalMailDto findById(int idInternalMail) throws Exception {
        InternalMail internalMail = internalMailRepository.findById(idInternalMail).orElse(new InternalMail());
        InternalMailDto internalMailDto = internalMailMapper.internalMailToDto(internalMail);
        return internalMailDto;
    }

    @Override
    public boolean respondMessage(Reply reply) throws Exception {
        InternalMail internalMail = internalMailRepository.findById(reply.getId()).orElseThrow(() -> new Exception("No existe mail con ese id."));;
        if (!internalMail.getSubjectReply().isEmpty()) throw new Exception("El mail ya fue respondido");
        internalMail.setSubjectReply(reply.getMessage());
        internalMailRepository.save(internalMail);
        return true;
    }
}
