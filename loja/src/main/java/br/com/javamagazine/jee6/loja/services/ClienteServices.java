package br.com.javamagazine.jee6.loja.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.javamagazine.jee6.loja.entity.Cliente;
import br.com.javamagazine.jee6.loja.exception.ClienteExistenteException;

@Stateless
public class ClienteServices {
	
	@PersistenceContext
	private EntityManager em;
	
	public Cliente findByEmailAndSenha(String email, String senha) {
		Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.email = :pEmail AND c.senha = :pSenha");
		query.setParameter("pEmail", email);
		query.setParameter("pSenha", senha);
		
		try {
			return (Cliente) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
			
	}
	
	public Cliente adicionar(Cliente cliente) throws ClienteExistenteException {
		try {
			em.persist(cliente);
			return cliente;
		} catch (PersistenceException e) {
			throw new ClienteExistenteException();
		}
	}

}
