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
public class Terminal extends IdentifiedObject{

    String sequenceNumber;
    String ConductingEquipmentRef;
    String ConnectivityNodeRef;
    PhaseCode phaseCode;
    
    
    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getConductingEquipmentRef() {
        return ConductingEquipmentRef;
    }

    public void setConductingEquipmentRef(String ConductingEquipmentRef) {
        this.ConductingEquipmentRef = ConductingEquipmentRef;
    }

    public String getConnectivityNodeRef() {
        return ConnectivityNodeRef;
    }

    public void setConnectivityNodeRef(String ConnectivityNodeRef) {
        this.ConnectivityNodeRef = ConnectivityNodeRef;
    }

    public PhaseCode getPhaseCode() {
        return phaseCode;
    }

    public void setPhaseCode(PhaseCode phaseCode) {
        this.phaseCode = phaseCode;
    }
    
}
