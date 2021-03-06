package com.modou.api;

import org.json.JSONException;
import org.json.JSONObject;

import com.modou.data.resolver.BaseRet;
import com.modou.data.resolver.CheckClientVersionRet;
import com.modou.data.resolver.MapFileRet;
import com.modou.data.resolver.RegisterRet;
import com.modou.data.resolver.UserRet;


/**
 * 数据转换接口
 * @author changqiang.li
 * 通过泛型的形式将服务器传递的数据转化为相应的数据对象
 * @param <T>
 */
public interface DataParseHandler<T> {

	public T parseRet(String data);
	
	abstract class JSONDataParseHandler<T> implements DataParseHandler<T> {
		@Override
		public T parseRet(String data) {
			try {
				JSONObject json = new JSONObject(data);
				return parseRet(json);
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}

		abstract T parseRet(JSONObject json);
	}
	
	/**基类信息回调*/
	public static final DataParseHandler<BaseRet> BASE_PARSE_HANDLER = new JSONDataParseHandler<BaseRet>() {
		@Override
		BaseRet parseRet(JSONObject json) {
			return new BaseRet(json);
		}
	};
	
	/**获取loading页图片信息回调*/
//	public static final DataParseHandler<LoadingImgRet> LOADING_IMG_PARSE_HANDLER = new JSONDataParseHandler<LoadingImgRet>() {
//		@Override
//		LoadingImgRet parseRet(JSONObject json) {
//			return new LoadingImgRet(json);
//		}
//	};
	
	/**版本更新检查数据回调*/
	public static final DataParseHandler<CheckClientVersionRet> CHECK_CLIENT_VERSIN_HANDLER = new JSONDataParseHandler<CheckClientVersionRet>() {
		@Override
		CheckClientVersionRet parseRet(JSONObject json) {
			return new CheckClientVersionRet(json);
		}
	};
	
	/**注册用户回调*/
	public static final DataParseHandler<RegisterRet> REGISTER_HANDLER = new JSONDataParseHandler<RegisterRet>() {
		RegisterRet parseRet(JSONObject json) {
			return new RegisterRet(json);
		}
	};
	
	/**用户登录回调*/
	public static final DataParseHandler<UserRet> LOGIN_HANDLER = new JSONDataParseHandler<UserRet>() {
		UserRet parseRet(JSONObject json) {
			return new UserRet(json);
		}
	};
	
	/**下载地图文件URL*/
	public static final DataParseHandler<MapFileRet> DL_MAP_URL_PARSE_HANDLER = new JSONDataParseHandler<MapFileRet>() {
		MapFileRet parseRet(JSONObject json) {
			return new MapFileRet(json);
		}
	};
	
//	/**修改密码回调*/
//	public static final DataParseHandler<UpdatePwdRet> UPDATE_PASSWORD_HANDLER = new JSONDataParseHandler<UpdatePwdRet>() {
//		UpdatePwdRet parseRet(JSONObject json) {
//			return new UpdatePwdRet(json);
//		}
//	};
//	
}
