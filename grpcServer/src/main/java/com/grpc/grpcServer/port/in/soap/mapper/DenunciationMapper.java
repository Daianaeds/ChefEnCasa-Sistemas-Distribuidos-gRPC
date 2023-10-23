package com.grpc.grpcServer.port.in.soap.mapper;

import com.grpc.grpcServer.entities.Denunciation;
import com.grpc.grpcServer.port.in.soap.dtos.DenunciationDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DenunciationMapper {

    public List<DenunciationDto> denunciationListToDto(List<Denunciation> denunciationList) {
        List<DenunciationDto> denunciationDtoList = new ArrayList<>() ;

        for (Denunciation denunciation : denunciationList) {
            DenunciationDto denunciationDto = denunciationToDto(denunciation);
            denunciationDtoList.add(denunciationDto);
        }

        return denunciationDtoList;
    }

    private DenunciationDto denunciationToDto(Denunciation denunciation) {
      return DenunciationDto.builder()
                .id(denunciation.getId())
                .idRecipe(denunciation.getRecipe().getId())
                .motive(denunciation.getMotive())
                .title(denunciation.getRecipe().getTitle())
                .username(denunciation.getUser().getUsername())
                .build();
    }
}
