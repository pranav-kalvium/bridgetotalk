package com.pranav.bridgetotalk.adapter.in.web.mapper;

import com.pranav.bridgetotalk.adapter.in.web.dto.agent.AgentDto;
import com.pranav.bridgetotalk.application.port.in.command.CreateAgentCommand;
import com.pranav.bridgetotalk.domain.people.Agent;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AgentDtoMapper {

    CreateAgentCommand toCommand(AgentDto dto);

    AgentDto toDto(Agent agent);
}
