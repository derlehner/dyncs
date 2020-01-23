package at.ac.tuwien.big.ame.dyncs.server.util;

import java.io.IOException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UMLPackage.Literals;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ExampleDataGenerator implements ApplicationListener<ApplicationReadyEvent> {

  public static boolean DEBUG = true;

  protected static Resource loadResource() {
    ResourceSet set = new ResourceSetImpl();
    set.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
    set.getResourceFactoryRegistry().getExtensionToFactoryMap()
        .put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
        .put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);

    return set
        .getResource(URI.createFileURI("src/main/resources/uml-model-examples/ExtendedPO2.uml"),
            true);
  }

  protected static org.eclipse.uml2.uml.Package load(URI uri) {
    org.eclipse.uml2.uml.Package package_ = null;

    try {
      // Load the requested resource
      ResourceSet Resource_Set = new ResourceSetImpl();
      UMLResourcesUtil.init(Resource_Set);
      Resource resource = Resource_Set.getResource(uri, true);

      // Get the first (should be only) package from it
      package_ = (org.eclipse.uml2.uml.Package) EcoreUtil
          .getObjectByType(resource.getContents(), UMLPackage.Literals.CLASS);
    } catch (WrappedException we) {
      err(we.getMessage());
      System.exit(1);
    }

    return package_;
  }

  protected static void out(String format, Object... args) {
    if (DEBUG) {
      System.out.printf(format, args);
      if (!format.endsWith("%n")) {
        System.out.println();
      }
    }
  }

  protected static void err(String format, Object... args) {
    System.err.printf(format, args);
    if (!format.endsWith("%n")) {
      System.err.println();
    }
  }

  protected static Model createModel(String name) {
    Model model = UMLFactory.eINSTANCE.createModel();
    model.setName(name);

    out("Model '%s' created.", model.getQualifiedName());

    return model;
  }

  protected static Lifeline createLifeline(Package pck, String name) {
    Lifeline lifeline = (Lifeline) pck.createOwnedType(name, Literals.LIFELINE);
    return lifeline;
  }

  protected static org.eclipse.uml2.uml.Package createPackage(
      org.eclipse.uml2.uml.Package nestingPackage, String name) {
    org.eclipse.uml2.uml.Package package_ = nestingPackage.createNestedPackage(name);

    out("Package '%s' created.", package_.getQualifiedName());

    return package_;
  }

  protected static PrimitiveType createPrimitiveType(org.eclipse.uml2.uml.Package package_,
      String name) {
    PrimitiveType primitiveType = package_.createOwnedPrimitiveType(name);

    out("Primitive type '%s' created.", primitiveType.getQualifiedName());

    return primitiveType;
  }

  protected static Enumeration createEnumeration(org.eclipse.uml2.uml.Package package_,
      String name) {
    Enumeration enumeration = package_.createOwnedEnumeration(name);

    out("Enumeration '%s' created.", enumeration.getQualifiedName());

    return enumeration;
  }

  protected static EnumerationLiteral createEnumerationLiteral(Enumeration enumeration,
      String name) {
    EnumerationLiteral enumerationLiteral = enumeration.createOwnedLiteral(name);

    out("Enumeration literal '%s' created.", enumerationLiteral.getQualifiedName());

    return enumerationLiteral;
  }

  protected static org.eclipse.uml2.uml.Class createClass(org.eclipse.uml2.uml.Package package_,
      String name, boolean isAbstract) {
    org.eclipse.uml2.uml.Class class_ = package_.createOwnedClass(name, isAbstract);

    out("Class '%s' created.", class_.getQualifiedName());

    return class_;
  }

  protected static Generalization createGeneralization(Classifier specificClassifier,
      Classifier generalClassifier) {
    Generalization generalization = specificClassifier.createGeneralization(generalClassifier);

    out("Generalization %s --|> %s created.", specificClassifier.getQualifiedName(),
        generalClassifier.getQualifiedName());

    return generalization;
  }

  protected static Property createAttribute(org.eclipse.uml2.uml.Class class_, String name,
      Type type, int lowerBound, int upperBound) {
    Property attribute = class_.createOwnedAttribute(name, type, lowerBound, upperBound);

    out("Attribute '%s' : %s [%s..%s] created.", //
        attribute.getQualifiedName(), // attribute name
        type.getQualifiedName(), // type name
        lowerBound, // no special case for multiplicity lower bound
        (upperBound == LiteralUnlimitedNatural.UNLIMITED)
            ? "*" // special case for unlimited bound
            : upperBound);

    return attribute;
  }

  protected static Association createAssociation(Type type1, boolean end1IsNavigable,
      AggregationKind end1Aggregation, String end1Name, int end1LowerBound, int end1UpperBound,
      Type type2, boolean end2IsNavigable, AggregationKind end2Aggregation, String end2Name,
      int end2LowerBound, int end2UpperBound) {

    Association association = type1
        .createAssociation(end1IsNavigable, end1Aggregation, end1Name, end1LowerBound,
            end1UpperBound,
            type2, end2IsNavigable, end2Aggregation, end2Name, end2LowerBound, end2UpperBound);

    out("Association %s [%s..%s] %s-%s %s [%s..%s] created.", //
        UML2Util.isEmpty(end1Name)
            // compute a placeholder for the name
            ? String.format("{%s}", type1.getQualifiedName()) //
            // user-specified name
            : String.format("'%s::%s'", type1.getQualifiedName(), end1Name), //
        end1LowerBound, // no special case for this
        (end1UpperBound == LiteralUnlimitedNatural.UNLIMITED)
            ? "*" // special case for unlimited upper bound
            : end1UpperBound, // finite upper bound
        end2IsNavigable
            ? "<" // indicate navigability
            : "-", // not navigable
        end1IsNavigable
            ? ">" // indicate navigability
            : "-", // not navigable
        UML2Util.isEmpty(end2Name)
            // compute a placeholder for the name
            ? String.format("{%s}", type2.getQualifiedName()) //
            // user-specified name
            : String.format("'%s::%s'", type2.getQualifiedName(), end2Name), //
        end2LowerBound, // no special case for this
        (end2UpperBound == LiteralUnlimitedNatural.UNLIMITED)
            ? "*" // special case for unlimited upper bound
            : end2UpperBound);

    return association;
  }

  protected static void save(org.eclipse.uml2.uml.Package package_, URI uri) {
    // Create a resource-set to contain the resource(s) that we are saving
    ResourceSet resourceSet = new ResourceSetImpl();

    // Initialize registrations of resource factories, library models,
    // profiles, Ecore metadata, and other dependencies required for
    // serializing and working with UML resources. This is only necessary in
    // applications that are not hosted in the Eclipse platform run-time, in
    // which case these registrations are discovered automatically from
    // Eclipse extension points.
    UMLResourcesUtil.init(resourceSet);

    // Create the output resource and add our model package to it.
    Resource resource = resourceSet.createResource(uri);
    resource.getContents().add(package_);

    // And save
    try {
      resource.save(null); // no save options needed
      out("Done.");
    } catch (IOException ioe) {
      err(ioe.getMessage());
    }
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    Model epo2Model = createModel("epo2");
    org.eclipse.uml2.uml.Package barPackage = createPackage(epo2Model, "bar");
    PrimitiveType intPrimitiveType = createPrimitiveType(epo2Model, "int");
    PrimitiveType stringPrimitiveType = createPrimitiveType(epo2Model, "string");
    Enumeration orderStatusEnumeration = createEnumeration(epo2Model, "OrderStatus");
    createEnumerationLiteral(orderStatusEnumeration, "Pending");
    org.eclipse.uml2.uml.Class supplierClass = createClass(epo2Model, "Supplier", false);
    createAttribute(supplierClass, "name", stringPrimitiveType, 0, 1);
    save(epo2Model, URI
        .createFileURI("").appendSegment("ExtendedPO2")
        .appendFileExtension(UMLResource.FILE_EXTENSION));

  }
}
