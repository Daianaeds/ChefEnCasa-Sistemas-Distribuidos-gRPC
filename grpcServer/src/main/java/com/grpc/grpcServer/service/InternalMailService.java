package com.grpc.grpcServer.service;

import com.grpc.grpcServer.RecipeRequest;
import com.grpc.grpcServer.port.in.rest.dto.InternalMailDto;
import com.grpc.grpcServer.port.in.rest.dto.Reply;

import java.util.List;

public interface InternalMailService {

    boolean newInternalMail(InternalMailDto request) throws Exception;

    public List<InternalMailDto> findByDestination(String destination) throws Exception;

    public List<InternalMailDto> findBySource(String source) throws Exception;

    InternalMailDto findById(int idInternalMail) throws Exception;

    boolean respondMessage(Reply reply) throws Exception;
}
