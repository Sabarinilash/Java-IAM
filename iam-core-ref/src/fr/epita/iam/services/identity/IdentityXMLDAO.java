/**
 * Ce fichier est la propriété de Thomas BROUSSARD Code application : Composant :
 */
package fr.epita.iam.services.identity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.EntityCreationException;
import fr.epita.iam.exceptions.EntityDeletionException;
import fr.epita.iam.exceptions.EntityReadException;
import fr.epita.iam.exceptions.EntitySearchException;
import fr.epita.iam.exceptions.EntityUpdateException;
import fr.epita.iam.services.conf.ConfKey;
import fr.epita.iam.services.conf.ConfigurationService;

/**
 * <h3>Description</h3>
 * <p>
 * This class allows to ...
 * </p>
 *
 * <h3>Usage</h3>
 * <p>
 * This class should be used as follows:
 *
 * <pre>
 * <code>${type_name} instance = new ${type_name}();</code>
 * </pre>
 * </p>
 *
 * @since $${version}
 * @see See also $${link}
 * @author ${user}
 *
 *         ${tags}
 */
public class IdentityXMLDAO implements IdentityDAO {

	private Document document;
	/**
	 *
	 */
	public IdentityXMLDAO() {
		final List<Identity> results = new ArrayList<>();
		document = null;

		try {
			document = getDocument();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// FIXME Use a logger to trace the following error
			// LOGGER.error("An error occured", ${exception_var})
			// ignore this at this moment, but it is to be converted to a "DaoInitializationException"
		}
	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#create(java.lang.Object)
	 */
	@Override
	public void create(Identity entity) throws EntityCreationException {
		Document doc = null;
		try {
			doc = getDocument();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new EntityCreationException(entity, e);
		}
		final Node rootNode = doc.getElementsByTagName("identities").item(0);
		final Element newIdentity = doc.createElement("identity");
		final Element displayNameElement = doc.createElement("displayName");
		final Element emailElement = doc.createElement("email");
		final Element uidElement = doc.createElement("uid");

		newIdentity.appendChild(displayNameElement);
		newIdentity.appendChild(emailElement);
		newIdentity.appendChild(uidElement);

		displayNameElement.setTextContent(entity.getDisplayName());
		emailElement.setTextContent(entity.getEmail());
		uidElement.setTextContent(entity.getUid());

		rootNode.appendChild(newIdentity);

		writeDoc(doc);
	}

	/** 
	 * <h3>Description</h3>  
	 * <p>This methods allows to ...</p>
	 *
	 * <h3>Usage</h3>
	 * <p>It should be used as follows :
	 *   
	 * <pre><code> ${enclosing_type} sample;
	 *
	 * //...
	 *
	 * sample.${enclosing_method}();
	 *</code></pre>
	 * </p>
	 *  
	 * @since $${version}
	 * @see Voir aussi $${link}
	 * @author ${user}
	 *
	 * ${tags}
	 */
	private void writeDoc(Document doc) {
		try {
			final PrintWriter pw = new PrintWriter(ConfigurationService.getProperty(ConfKey.XML_BACKEND_FILE));
			pw.write(docToString(doc));
			pw.flush();
			pw.close();
		} catch (final FileNotFoundException e) {
			// TODO trace exception
		}
	}

	private static String docToString(Document doc) {
		try {
			final StringWriter sw = new StringWriter();
			final TransformerFactory tf = TransformerFactory.newInstance();
			final Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			return sw.toString();
		} catch (final Exception ex) {
			throw new RuntimeException("Error converting to String", ex);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(Identity entity) throws EntityDeletionException {

	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#update(java.lang.Object)
	 */
	@Override
	public void update(Identity entity) throws EntityUpdateException {

	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#getById(java.io.Serializable)
	 */
	@Override
	public Identity getById(Serializable id) throws EntityReadException {
		return new Identity();
	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#search(java.lang.Object)
	 */
	@Override
	public List<Identity> search(Identity criteria) throws EntitySearchException {
		Document doc = null;
		try {
			doc = getDocument();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// FIXME Use a logger to trace the following error
			// LOGGER.error("An error occured", ${exception_var})
		}

		final String xpathExpression = "/identities/identity[ ./email/text() = '" + criteria.getEmail()
		+ "' or contains(./displayName/text(), '" + criteria.getDisplayName() + "')]";
		final List<Element> elements = getElements(doc, xpathExpression);

		final List<Identity> results = new ArrayList<>();

		for (final Element element : elements) {
			final Identity identity = getIdentityFromElement(element);
			results.add(identity);
		}

		return results;
	}

	/**
	 * <h3>Description</h3>
	 * <p>
	 * This methods allows to ...
	 * </p>
	 *
	 * <h3>Usage</h3>
	 * <p>
	 * It should be used as follows :
	 *
	 * <pre>
	 * <code> ${enclosing_type} sample;
	 *
	 * //...
	 *
	 * sample.${enclosing_method}();
	 *</code>
	 * </pre>
	 * </p>
	 *
	 * @since $${version}
	 * @see Voir aussi $${link}
	 * @author ${user}
	 *
	 *         ${tags}
	 */
	private Identity getIdentityFromElement(Element item) {
		final Identity identity = new Identity();
		final String displayName = item.getElementsByTagName("displayName").item(0).getTextContent();
		final String email = item.getElementsByTagName("email").item(0).getTextContent();
		final String uid = item.getElementsByTagName("uid").item(0).getTextContent();
		identity.setDisplayName(displayName);
		identity.setEmail(email);
		identity.setUid(uid);
		return identity;
	}

	private List<Element> getElements(Document doc, String xpathExpr) {
		final XPathFactory xpathFactory = XPathFactory.newInstance();
		javax.xml.xpath.XPathExpression expr;
		try {
			expr = xpathFactory.newXPath().compile(xpathExpr);
			final NodeList xpathEval = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			final List<Element> results = new ArrayList<>();
			for (int i = 0; i < xpathEval.getLength(); i++) {
				results.add((Element) xpathEval.item(i));
			}
			return results;
		} catch (final XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<>();

	}

	/**
	 * <h3>Description</h3>
	 * <p>
	 * This methods allows to ...
	 * </p>
	 *
	 * <h3>Usage</h3>
	 * <p>
	 * It should be used as follows :
	 *
	 * <pre>
	 * <code> ${enclosing_type} sample;
	 *
	 * //...
	 *
	 * sample.${enclosing_method}();
	 *</code>
	 * </pre>
	 * </p>
	 *
	 * @since $${version}
	 * @see Voir aussi $${link}
	 * @author ${user}
	 *
	 *         ${tags}
	 */
	private Document getDocument() throws ParserConfigurationException, SAXException, IOException, FileNotFoundException {
		final File file = new File(ConfigurationService.getProperty(ConfKey.XML_BACKEND_FILE));
		if (!file.exists()) {
			file.createNewFile();
		}
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();
		final Document document = db.parse(new FileInputStream(file));
		return document;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.services.identity.IdentityDAO#healthCheck()
	 */
	@Override
	public boolean healthCheck() {
		return true;
	}

}
