package br.com.infosolo.comum.component.hibernate;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Classe suporte a todas as entidades.
 */
@XmlTransient
public abstract class BaseEntity implements IEntity {

        private static final long serialVersionUID = 1L;

        // ATTRIBUTES ****************************/
        
        @XmlTransient
        private boolean dirty = false;

        boolean isDirty() {
                return dirty;
        }

        void makeDirty() {
                dirty = true;
        }

        void cleanDirty() {
                dirty = false;
        }
        
        // PUBLIC METHODS ****************************/

        @Override
        public String toString() {
                StringBuilder s = new StringBuilder();
                s.append(getClass().getName()).append("[id = ").append(getId()).append("]");
                return s.toString();
        }

        @Override
        public int hashCode() {
                Object id = getId();
                final int prime = 37;
                int result = 1;
                result = prime * result + ((id == null) ? 0 : id.hashCode());
                return result;
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj) {
                        return true;
                }
                if (obj == null) {
                        return false;
                }
                
                Class<?> objClass = null;//HibernateProxyExtractor.extractClass(obj);
                
                if (getClass() != objClass) {
                        return false;
                }
                
                final BaseEntity other = (BaseEntity) obj;
                Object id = getId();
                if (id == null) {
                        if (other.getId() != null)
                                return false;
                }else if (!id.equals(other.getId())) {
                        return false;
                }
                return true;
        }

}
