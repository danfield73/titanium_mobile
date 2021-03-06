/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by 
 * Appcelerator. It should not be modified by hand.
 */
package ${config['appid']};

import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.KrollModuleInfo;
import org.appcelerator.kroll.KrollRuntime;

% if runtime == "v8":
import org.appcelerator.kroll.runtime.v8.V8Runtime;
% else:
import org.appcelerator.kroll.runtime.rhino.RhinoRuntime;
% endif

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

public final class ${config['classname']}Application extends TiApplication
{
	private static final String TAG = "${config['classname']}Application";

	@Override
	public void onCreate()
	{
		super.onCreate();

		appInfo = new ${config['classname']}AppInfo(this);
		postAppInfo();

		% if runtime == "v8":
		KrollRuntime.init(this, new V8Runtime());
		% else:
		KrollRuntime.init(this, new RhinoRuntime());
		% endif

		stylesheet = new ApplicationStylesheet();
		postOnCreate();

		<%def name="onAppCreate(module)" filter="trim">
			% if module['on_app_create'] != None:
			try {
				${module['class_name']}.${module['on_app_create']}(this);
			} catch (Exception e) {
				StringBuilder error = new StringBuilder();
				error.append("Error running onAppCreate method ")
					.append("\"${module['on_app_create']}\"")
					.append(" for module ")
					.append("${module.get('api_name') or module.get('manifest').moduleid}: ")
					.append(e.getMessage());
				Log.e(TAG, error.toString(), e);
			}
			% endif
		</%def>

		% for module in app_modules:
		${onAppCreate(module)} \
		% endfor

		% for module in custom_modules:
		${onAppCreate(module)} \
		% endfor
	}

	/*
	@Override
	protected void bootModules(TiContext context)
	{
		% for module in app_modules:
		// ${module['api_name']} module
		modules.add(new ${module['class_name']}(context));
			% for child_module in module['external_child_modules']:
		// ${module['api_name']}.${child_module['name']}
		KrollModule.addExternalChildModule(${module['class_name']}.class, ${child_module['proxyClassName']}.class);
			% endfor
		% endfor

		% if len(custom_modules) > 0:
		// Custom modules
		KrollModuleInfo moduleInfo;
		% endif

		% for module in custom_modules:
		<% manifest = module['manifest'] %>
		moduleInfo = new KrollModuleInfo(
			"${manifest.name}", "${manifest.moduleid}", "${manifest.guid}", "${manifest.version}",
			"${manifest.description}", "${manifest.author}", "${manifest.license}",
			"${manifest.copyright}");

		% if manifest.has_property("licensekey"):
		moduleInfo.setLicenseKey("${manifest.licensekey}");
		% endif

		KrollModule.addModuleInfo(moduleInfo);
		% endfor

		% if config['deploy_type'] != 'production':
		org.appcelerator.titanium.TiVerify verify = new org.appcelerator.titanium.TiVerify(context.getActivity(), this);
		verify.verify();
		modules.add(new ti.modules.titanium.debug.DebugModule(context));
		% endif
	}

	% if len(custom_modules) > 0:
	@Override
	public KrollModule requireModule(TiContext context, KrollModuleInfo info)
	{
		KrollModule module = super.requireModule(context, info);
		if (module != null) {
			return module;
		}

		String id = info.getId();
		% for module in custom_modules:
		if ("${module['manifest'].moduleid}".equals(id)) {
			module = new ${module['class_name']}(context);
		}
		% endfor

		if (module != null) {
			modules.add(module);
			return module;
		}
		return null;
	}
	% endif
	*/
}
