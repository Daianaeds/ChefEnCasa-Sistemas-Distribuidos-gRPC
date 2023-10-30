package com.grpc.grpcServer.port.in.rest.mapper;

import com.grpc.grpcServer.entities.InternalMail;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.port.in.rest.dto.InternalMailDto;
import com.grpc.grpcServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InternalMailMapper {

    @Autowired
    UserService userService;

    public List<InternalMailDto> InternalMailListToDto(List<InternalMail> internalMailList) {
        List<InternalMailDto> internalMailDtoList = new ArrayList<>() ;

        for (InternalMail internalMail : internalMailList) {
            InternalMailDto internalMaildto = internalMailToDto(internalMail);
            internalMailDtoList.add(internalMaildto);
        }

        return internalMailDtoList;
    }

    public InternalMailDto internalMailToDto(InternalMail internalMail) {
        return InternalMailDto.builder()
                .id(internalMail.getId())
                .destination(internalMail.getDestination().getUsername())
                .source(internalMail.getSource().getUsername())
                .subject(internalMail.getSubject())
                .subjectReply(internalMail.getSubjectReply())
                .build();
    }

    public InternalMail dtoTointernalMailNew(InternalMailDto internalMailDto) {
        User destination = userService.findByUsername(internalMailDto.getDestination());
        User source = userService.findByUsername(internalMailDto.getSource());

        return InternalMail.builder()
                .destination(destination)
                .source(source)
                .subject(internalMailDto.getSubject())
                .subjectReply(internalMailDto.getSubjectReply())
                .build();
    }
}
