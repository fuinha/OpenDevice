/*
 *
 *  * ******************************************************************************
 *  *  Copyright (c) 2013-2014 CriativaSoft (www.criativasoft.com.br)
 *  *  All rights reserved. This program and the accompanying materials
 *  *  are made available under the terms of the Eclipse Public License v1.0
 *  *  which accompanies this distribution, and is available at
 *  *  http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  *  Contributors:
 *  *  Ricardo JL Rufino - Initial API and Implementation
 *  * *****************************************************************************
 *
 */

package br.com.criativasoft.opendevice.middleware.test;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.tooling.GlobalGraphOperations;

/**
 * @author Ricardo JL Rufino on 01/05/15.
 */
public class PrintDataNative {

    public static void main(String[] args) {

        String path = "/media/Dados/Codigos/Java/Projetos/OpenDevice/Workspace/database";
        final GraphDatabaseService graphDb;
        graphDb =  new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(path)
                .setConfig(GraphDatabaseSettings.allow_store_upgrade, "false")
                .newGraphDatabase();
        final Transaction tx = graphDb.beginTx();


        try {
            ResourceIterable<Label> labels = GlobalGraphOperations.at(graphDb).getAllLabels();

//            Result result = graphDb.execute("match (n {name: 'my node'}) return n, n.name" );

            System.out.println("Labels : \n =========================");
            for (Label label : labels) {
                System.out.println(label);
            }

            System.out.println("Nodes : \n =========================");
                for (final Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {

                        System.out.print(node.getId() + ": ");
                        for (final String key : node.getPropertyKeys()) {
                            System.out.print(key + " - " + node.getProperty(key) + ", ");
                        }
                        System.out.print("\n");
                }
            tx.success();
        } finally {
            tx.finish();
            graphDb.shutdown();
        }
    }
}
