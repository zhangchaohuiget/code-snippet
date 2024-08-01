package com.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RelationshipEntity(type = "NodeRelationShip")
public class NodeRelationShip {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private BaseNode startNode;

    @EndNode
    private BaseNode endNode;

    @Property
    private String relation;

    @Property
    private String name;

    @Property
    private String description;
}
