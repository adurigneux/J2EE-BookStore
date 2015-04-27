Bookstore
==========================
Auteurs
:	Antoine Durigneux
:	Clement Dupont

Introduction
--------------------------

Le but de ce TP est de construire une application en ligne de vente de livres.
Le projet va être construit grâce à l'aide du framework J2EE en utilisant le serveur Glassfish et les bases de données JDBC.
Le principe est de coupler les pages JSP et du HTML en y ajoutant du code Java.

Utilisation
--------------------------

Afin de tester notre application, nous avons mis à votre disposition trois scripts :
:	1-start-database.bat lance la base de données
:	2-start-domain.bat lance le serveur et le domaine
:	3-deploy.bat déploie l'application

Il permettra de lancer toutes les ressources nécessaires à l'accès de l'application à l'aide d'un navigateur internet.


Architecture
--------------------------

###Configuration

On peut facilement séparer le projet en deux sous-parties d'importance égale :
:	Les pages JSP qui afficheront le contenu HTML des fonctionnalités implémentées
:	Les classes Java qui vont former les entitées nécessaires sous la forme de classes simples et de servlets

###Polymorphisme

Plusieurs interfaces ont été utilisées dans le projet :

	L'interface BookDal décrit les interactions possibles avec la base de données et la table des livres.
		public interface BookDal {
			...
		}

	L'interface ClientDal permet d'intéragir avec la base de données et la table des clients
		public interface ClientDal {
			...
		}

	L'interface CommandDal décrit les interactions possibles avec la base de données et la table des livres
		public interface CommandDal {
			...
		}
		
	De plus, chaque entitée (spécifiée avec @Entity) suit l'interface Serializable. 
	Cela va permettre à l'entitée d'être transformé sous la forme d'un tableau d'octet, ce qui nous permettre de les stocker dans la base de données.
		
		public class Book implements Serializable {
			...
		}
		
		public class Client implements Serializable {
			...
		}
		
		public class Command implements Serializable {
			...
		}

Vu que nous travaillons sur J2EE, nous avons donc utiliser le mécanisme de servlets Java.
Liste des servlets disponibles :
:	BookAuthors
:	BookIndex
:	BookInit
: 	BookListing
:	BookRegistration
:	BookSearch
:	BookShopping

	Voici la signature de la classe BookIndex :
	
		@WebServlet(name = "BookIndex", urlPatterns = {"/"})
		public class BookIndex extends HttpServlet {
			public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				...
			}
			public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				...
			}
		}

###Erreurs

	Deux exceptions sont disponibles dans le projet :
	-> BookAlreadyExistException utilisée quand un livre existe deja dans la base de données
	-> NoClientExistException utilisée afin de verifier si un client existe ou pas

	Méthode init de la classe BookDalImpl
	Exception levé si la base de donnée est déjà initialisé
		try {
            if (em.find(Book.class, "Les gardiens de la galaxie T1") != null) {
                em.flush();
                return false;
            }
        } catch (Exception e) {
            throw new BookAlreadyExistException();
        }
		
	Méthode addBook de la classe BookDalImpl
	Si la requête ne trouve pas de livre, elle retourne l'exception NoResult qui va déployer l'ajout du livre
		try {
            q.getSingleResult();
            throw new BookAlreadyExistException();
        } catch (NoResultException e) {
            em.persist(new Book(title, authorName, year));
            em.flush();
        }
		
	Méthode findBook de la classe BookDalImpl	
	Si la requete ne trouve pas le livre en question, l'exception NoResult va être traité par le retour de la valeur null
		try {
            Book b = (Book) q.getSingleResult();
            em.flush();
            return b;
        } catch (NoResultException e) {
            // S'il n'existe pas de livre alors on retourne un null
            return null;
        }
		
	Méthode findBooks de la classe BookDalImpl
	Peut importe le retour de la requête, on retourne la liste rempli ou vide 
		try {
            books.addAll((List<Book>) q.getResultList());
            em.flush();
            books.size();
            return books;
        } catch (NoResultException e) {
            // S'il n'existe pas de livre
            return books;
        }
		
	Méthode init de la classe ClientDalImpl
	Retourne false si l'ajout des utilisateurs 
		try {
            //admin
            addClient("clement", "bonjour");
            setAdmin("clement");

            //user
            addClient("antoine", "bonjour");
        } catch (Exception e) {
            return false;
        }
		
	Méthode getClientByUsername de la classe BookDalImpl
	Si le client n'existe pas, la fonction retourne l'exception NoClientExistException
		try {
            Client client = (Client) q.getSingleResult();
            em.flush();
            return client;
        } catch (NoResultException e) {
            throw new NoClientExistException();
        }
		
	Méthode addClient de la classe ClientDalImpl
	Si aucun client n'est retourné par la requete, l'exception est traité par l'ajout d'un nouveau client
		try {
            user = getClientByUsername(username);
            throw new Exception();
        } catch (NoClientExistException e) {
            // S'il n'existe pas d'utilisateur alors on l'ajoute
            user = new Client(username, password);
            em.persist(user);
            em.flush();
            return user;
        }
		
	Méthode setAdmin de la classe ClientDalImpl
	On entoure la fonction getClientByUsername qui peut retourner l'exception NoClientExistException. 
	Donc si l'exception est levée, la fonction retournera false
		try {
            Client user = getClientByUsername(username);
            user.setAdmin(true);
            em.persist(user);
            em.flush();
            return true;
        } catch (NoClientExistException e) {
            return false;
        }
		
	Méthode login de la classe ClientDalImpl
	Si l'identification ou la recuperation du client s'est mal passé, on retourne une exception
		Client client = getClientByUsername(username);
        if (client.getPassword().compareTo(password) == 0) {
            currentClient = client;
            return client;
        }
        throw new Exception();

	Méthode addToCart de la classe CommandDalImpl
	Si la récuperation du livre a échoué, on quitte simplement la fonction
		try {
            Book book = (Book) q.getSingleResult();
            em.flush();
            cart.add(book);
        } catch (NoResultException e) {
			return;
        }
		
	Méthode doGet de la classe BookAuthors
	Si la méthode getAuthors retourne une exception, on va alors assigné la valeur true au flag erreur pour la gestion dans des erreurs dans les servlets
		try {
            List<String> authors = bookSession.getAuthors();
            request.setAttribute("authors", authors);
        } catch (Exception e) {
            request.setAttribute("error", true);
        }
		
	Méthode doPost de la classe BookIndex
	Si la fonction login retourne une exception, le catch va assigné à la chaine de caractère l'intitulé de l'erreur
		try {
            clientSession.login(username, password);
            request.getSession(false).setAttribute("client", clientSession.getCurrentClient());
		} catch (Exception ignore) {
            error = "Erreur de pseudo/mot de passe. Utilisateur inconnu.";
        }
		
	Méthode doGet de la classe BookShopping
	Si la méthode addToCart retourne une exception, le catch va assigné à la chaine de caractère l'intitulé de l'infocmd
		try {
            cmdSession.addToCart(request.getParameter("title"));
            returnView = "/booklist";
        } catch (Exception e) {
            infocmd = "une erreur est survenue lors de l'ajout du livre";
        }
		
Code samples
--------------------------

###1. Création de requetes sur la base de données grâce à aux objets Entity

	// Chaque entitée Book va être stockée dans la table BOOK
	@Table(name = "BOOK")
	@Entity
	// Liste de requetes pré-définies qui va servir pour tous les servlets
	@NamedQueries(value = {
		// Sélection de tous les livres
		@NamedQuery(name = "allBooks", query = "SELECT b FROM Book b"),
		// Sélection par titre
		@NamedQuery(name = "findByTitle", query = "SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:title)"),
		// Sélection par morceau de titre
		@NamedQuery(name = "findByTitlePart", query = "SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(:title)"),
		// Sélection par auteur
		@NamedQuery(name = "findByAuthor", query = "SELECT b FROM Book b WHERE LOWER(b.author) = LOWER(:author)"),
		// Sélection de tous les auteurs
		@NamedQuery(name = "allAuthors", query = "SELECT DISTINCT(b.author) FROM Book b"),})
	public class Book implements Serializable {
		...
	}

###2. Servlet BookShopping

	Méthode goGet de la classe BookShopping
		
		// Diverses actions sont disponibles (add, del, cmd et view)
		switch (request.getParameter("action")) {
			case "add":
				try {
					// Ajout du livre au panier
					cmdSession.addToCart(request.getParameter("title"));
					// Redirection vers le listing des livres
					returnView = "/booklist";
				} catch (Exception e) {
					...
				}
				break;
			case "del":
				try {
					// Suppression du livre au panier
					cmdSession.removeFromCart(request.getParameter("title"));
					// Redirection vers le panier
					returnView = "/cart.jsp";
				} catch (Exception e) {
					...
				}
				break;
			case "cmd":
				try {
					// Commande des livres
					cmdSession.saveCommand(clientsession.getCurrentClient());
					// On retourne sur le panier
					returnView = "/cart.jsp";
				} catch (Exception e) {
					...
				}
				break;
			case "list":
				try {
					// Listing des commandes
					request.setAttribute("commands", cmdSession.getCommands());
					// On retourne sur les commandes
					returnView = "/commands.jsp";
				} catch (Exception e) {
					...
				}
				break;
			case "view":
				try {
					// Listing des livres 
					request.setAttribute("books", cmdSession.getCart());
					// On retourne vers le panier
					returnView = "/cart.jsp";
				} catch (Exception e) {
					...
				}
				break;
		}
		
###3. Exemple de JSP : cart.jsp

	<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	// Ajout du header pour l'ajout du menu et des balises HTML
	<%@include file="header.jsp" %>

	<h2>Contenu du panier :</h2>
	// On inclut des tags JSP
	<c:choose>
		// S'il y a un message à afficher, on l'affiche
		<c:when test="${message != null}">
			<h3>${message}</h3>
		</c:when> 
		
		<c:otherwise>
			Si la liste des livres est vide, on affiche rien, sinon on affiche
			<c:if test="${not empty books}">
				<ul>
					// Pour chaque livre de la liste
					<c:forEach var="book"  items="${books}" >
						// Affichage des infos du livres
						<li> <c:out value="${book.title}"/> par <c:out value="${book.author}"/> [<c:out value="${book.yearOfProd}"/>] <a href="<c:url value="/cart?action=del&title=${book.title}"/>">Retirer du panier</a></li>
					</c:forEach>
				</ul>
				// Lien pour le passage de la commande
				<a href="<c:url value="/cart?action=cmd"/>">Passer la commande</a>
			</c:if>
		</c:otherwise>    
		
	</c:choose>
	// Inclusion du footer pour fermes les balises HTML
	<%@include file="footer.jsp" %>
	
###4. Implémentation de l'interface BookDal et initialisation de la base des livres

	@Remote(BookDal.class)
	@Stateless
	public class BookDalImpl implements BookDal {
		// On récupere le gestionnaire d'entitées qui accessible en tout point du projet
		@PersistenceContext(unitName = "BookStorePU")
		private EntityManager em;

		@Override
		public boolean init() throws Exception {
			// If exist, do not add again
			try {
				// Recherche de l'objet Book
				if (em.find(Book.class, "Les gardiens de la galaxie T1") != null) {
					// le livre est deja présent
					em.flush();
					return false;
				}
			} catch (Exception e) {
				throw new BookAlreadyExistException();
			}
			On ajoute un par un les livres pré-définis
			em.persist(new Book("Les gardiens de la galaxie T1", "M. Bendis", 2014));
			em.persist(new Book("Les gardiens de la galaxie T2", "M. Bendis", 2015));
			em.persist(new Book("Nova T1", "Loeb", 2015));
			em.persist(new Book("Avengers T1", "Hickman", 2015));
			em.persist(new Book("Superior Spiderman T1", "Slott", 2015));
			em.flush();
			return true;
		}
	}
	
###5. Utilisation des sessions

	Méthode doPost de la classe BookIndex
	
		String param = request.getParameter("formName");
		...
		// On regarde le type de requete de requete souhaité
		switch (param) {
			
            case "login": {
                try {
                    clientSession.login(username, password);
					// Affection du client dans la session
                    request.getSession(false).setAttribute("client", clientSession.getCurrentClient());
                } catch (Exception ignore) {
                    ...
                }
            }
            break;
            case "logout": {
				// Suppresion de la session
                request.getSession().invalidate();
            }
            break;

			...
        }