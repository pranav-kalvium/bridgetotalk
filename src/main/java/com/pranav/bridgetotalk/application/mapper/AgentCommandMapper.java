package com.pranav.bridgetotalk.application.mapper;

import com.pranav.bridgetotalk.adapter.out.jpa.entity.AgentJpaEntity;
import com.pranav.bridgetotalk.application.port.in.command.CreateAgentCommand;
import com.pranav.bridgetotalk.domain.people.Agent;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AgentCommandMapper {

    default Agent toDomain(CreateAgentCommand command){
        return Agent.createNew(
                command.companyId(),
                command.name(),
                command.email(),
                command.passwordHash(),
                command.role());
    }
}
