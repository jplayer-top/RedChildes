package com.oblivion.redchildpuls.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/8.
 */
public class AddressResponse implements IResponse {

    /**
     * addressList : [{"addressArea":"赤水市","addressDetail":"文华路文华学院","city":"遵义市","id":134,"isDefault":0,"name":"张瑞丽","phoneNumber":"189","province":"贵州省","zipCode":"563000"},{"addressArea":"密云县","addressDetail":"街道口地铁c口","city":"北京市","id":139,"isDefault":0,"name":"itcast","phoneNumber":"027-81970008","province":"北京市","zipCode":"100000"},{"addressArea":"洪山区","addressDetail":"街道口","city":"武汉","id":146,"isDefault":0,"name":"肖文","phoneNumber":"15801477821","province":"湖北","zipCode":"430070"},{"addressArea":"二七区","addressDetail":"fjghkk","city":"郑州市","id":147,"isDefault":1,"name":"truth tst","phoneNumber":"44554","province":"河南省","zipCode":"450000"},{"addressArea":"二七区","addressDetail":"BBB BBB","city":"郑州市","id":148,"isDefault":0,"name":"zzz","phoneNumber":"254649","province":"河南省","zipCode":"450000"},{"addressArea":"二七区","addressDetail":"Finn","city":"郑州市","id":149,"isDefault":0,"name":"thirty","phoneNumber":"56545","province":"河南省","zipCode":"450000"},{"addressArea":"二七区","addressDetail":"fog","city":"郑州市","id":150,"isDefault":0,"name":"see","phoneNumber":"123","province":"河南省","zipCode":"450000"},{"addressArea":"二七区","addressDetail":"dog","city":"郑州市","id":151,"isDefault":0,"name":"huan","phoneNumber":"1383838383","province":"河南省","zipCode":"450000"},{"addressArea":"枞阳县","addressDetail":"shsc","city":"安庆市","id":154,"isDefault":0,"name":"z","phoneNumber":"23565655","province":"安徽省","zipCode":"246000"},{"addressArea":"潮安县","addressDetail":"JHVH","city":"潮州市","id":156,"isDefault":0,"name":"ah","phoneNumber":"13232313","province":"广东省","zipCode":"515600"},{"addressArea":"积石山保安族东乡族撒拉族自治县","addressDetail":"dsada","city":"临夏回族自治州","id":158,"isDefault":0,"name":"123123","phoneNumber":"312321","province":"甘肃省","zipCode":"731100"},{"addressArea":"安乡县","addressDetail":"TT TT TT","city":"常德市","id":159,"isDefault":0,"name":"ghr","phoneNumber":"18874533538","province":"湖南省","zipCode":"415000"},{"addressArea":"宝安区","addressDetail":"rewqt","city":"深圳市","id":160,"isDefault":0,"name":"uuu","phoneNumber":"12343224456","province":"广东省","zipCode":"518000"},{"addressArea":"","addressDetail":"3123","city":"安庆市","id":162,"isDefault":0,"name":"daq","phoneNumber":"231132312","province":"安徽省","zipCode":""},{"addressArea":"","addressDetail":"123","city":"安庆市","id":163,"isDefault":0,"name":"123","phoneNumber":"123","province":"安徽省","zipCode":""},{"addressArea":"大理市","addressDetail":"xxhg","city":"大理白族自治州","id":164,"isDefault":0,"name":"huan","phoneNumber":"66666666","province":"云南省","zipCode":"671000"},{"addressArea":"大安市","addressDetail":"DMC","city":"白城市","id":165,"isDefault":0,"name":"zzz","phoneNumber":"13333333666","province":"吉林省","zipCode":"137000"},{"addressArea":"浦城县","addressDetail":"dghh","city":"南平市","id":169,"isDefault":0,"name":"doubi","phoneNumber":"322432434234","province":"福建省","zipCode":"353000"},{"addressArea":"西秀区","addressDetail":"ertert","city":"安顺市","id":170,"isDefault":0,"name":"fgh","phoneNumber":"55555555","province":"贵州省","zipCode":"561000"},{"addressArea":"西秀区","addressDetail":"345345","city":"安顺市","id":171,"isDefault":0,"name":"sdf","phoneNumber":"3433423","province":"贵州省","zipCode":"561000"},{"addressArea":"建阳市","addressDetail":"sdfsdf","city":"南平市","id":172,"isDefault":0,"name":"dfcgsdfxdfvzxd","phoneNumber":"34324","province":"福建省","zipCode":"353000"},{"addressArea":"宝安区","addressDetail":"dfgdfg","city":"深圳市","id":173,"isDefault":0,"name":"sdfsdf","phoneNumber":"32425345345345","province":"广东省","zipCode":"518000"},{"addressArea":"洪山区","addressDetail":"街道口","city":"武汉","id":174,"isDefault":0,"name":"肖文","phoneNumber":"15801477821","province":"湖北","zipCode":"430070"},{"addressArea":"洪山区","addressDetail":"街道口","city":"武汉","id":175,"isDefault":0,"name":"肖文","phoneNumber":"15801477821","province":"湖北","zipCode":"430070"},{"addressArea":"洪山区","addressDetail":"街道口","city":"武汉","id":176,"isDefault":0,"name":"肖文","phoneNumber":"15801477821","province":"湖北","zipCode":"430070"}]
     * response : addressList
     */

    public String response;
    public List<AddressListBean> addressList;

    public static class AddressListBean {
        /**
         * addressArea : 赤水市
         * addressDetail : 文华路文华学院
         * city : 遵义市
         * id : 134
         * isDefault : 0
         * name : 张瑞丽
         * phoneNumber : 189
         * province : 贵州省
         * zipCode : 563000
         */

        public String addressArea;
        public String addressDetail;
        public String city;
        public int id;
        public int isDefault;
        public String name;
        public String phoneNumber;
        public String province;
        public String zipCode;
    }
}
