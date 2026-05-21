package com.pranav.bridgetotalk.application.port.in;

import com.pranav.bridgetotalk.adapter.in.web.dto.queue.QueueFilter;
import com.pranav.bridgetotalk.application.port.in.command.CreateQueueCommand;
import com.pranav.bridgetotalk.application.port.in.command.LinkQueueAgentCommand;
import com.pranav.bridgetotalk.application.port.in.command.UpdateQueueCommand;
import com.pranav.bridgetotalk.domain.attendance.Queue;
import com.pranav.bridgetotalk.domain.people.Agent;

import java.util.List;
import java.util.UUID;

public interface ManageQueueUseCase {

    Queue createQueue(CreateQueueCommand createQueueCommand);

    Queue updateQueue(UUID queueId, UpdateQueueCommand updateQueueCommand);

    List<Queue> filterQueuesByCompanyId(QueueFilter queueFilter, UUID companyId);

    List<Queue> getAllActiveQueuesFromCompany(UUID companyId);

    void deleteQueue(UUID queueId, UUID companyId);

    List<Agent> findAgentsByQueueId(UUID queueId);

    void linkAgentToQueue(LinkQueueAgentCommand linkQueueAgentCommand);

    void unlinkAgentFromQueue(UUID agentId, UUID queueId);
}