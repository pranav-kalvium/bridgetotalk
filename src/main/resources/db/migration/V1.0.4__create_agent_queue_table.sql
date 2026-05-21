-- Migration: Create agent_queues join table
-- Description: Links agents and queues (N:N relationship)
-- Author: Pranav
-- Date: 12/2025

CREATE TABLE agent_queues (
                              agent_id UUID NOT NULL,
                              queue_id UUID NOT NULL,

    -- Relationship metadata
                              priority INT DEFAULT 1 NOT NULL, -- 1 = High, 2 = Medium, etc.
                              added_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,

    -- Integrity Constraints
                              PRIMARY KEY (agent_id, queue_id), -- Composite key prevents duplicates

                              CONSTRAINT fk_aq_agent FOREIGN KEY (agent_id)
                                  REFERENCES agents(id) ON DELETE CASCADE,

                              CONSTRAINT fk_aq_queue FOREIGN KEY (queue_id)
                                  REFERENCES queues(id) ON DELETE CASCADE
);

-- Performance indices
-- The PK already indexes (agent_id, queue_id).
-- Index for reverse lookup (retrieve all agents assigned to a queue):
CREATE INDEX idx_aq_queue ON agent_queues(queue_id);

-- Comments
COMMENT ON TABLE agent_queues IS 'Associative table linking agents to their assigned queues (Many-to-Many)';
COMMENT ON COLUMN agent_queues.priority IS 'Priority level of the agent in this specific queue';