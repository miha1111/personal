/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectedUsagepoints;

/**
 *
 * @author adminmedved
 */
public class ConnectivityNode extends IdentifiedObject {

    public String getConnectedCircuits() {
        return connectedCircuits;
    }

    public void setConnectedCircuits(String connectedCircuits) {
        this.connectedCircuits = connectedCircuits;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getStitchingInfo() {
        return stitchingInfo;
    }

    public void setStitchingInfo(String stitchingInfo) {
        this.stitchingInfo = stitchingInfo;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }
    String connectedCircuits;
    NodeType nodeType;
    String stitchingInfo;
    String portName;
    
    
}
