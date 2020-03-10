package by.arhor.university.model;

import java.io.Serializable;

import by.arhor.university.core.Identifiable;

/**
 * Marker-interface
 */
public interface ModelObject<ID> extends Identifiable<ID>, Serializable {
}
