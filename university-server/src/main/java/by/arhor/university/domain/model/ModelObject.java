package by.arhor.university.domain.model;

import java.io.Serializable;

import by.arhor.core.Identifiable;

/**
 * Marker-interface
 */
public interface ModelObject<ID> extends Identifiable<ID>, Serializable {
}
