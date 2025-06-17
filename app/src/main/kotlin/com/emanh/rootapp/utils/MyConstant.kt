package com.emanh.rootapp.utils

import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.composable.PrimaryLibraryData
import com.emanh.rootapp.presentation.composable.STFCarouselHeroThumbData
import com.emanh.rootapp.presentation.composable.STFCarouselThumbData
import com.emanh.rootapp.presentation.composable.SecondaryLibraryData
import com.emanh.rootapp.presentation.ui.home.HomePoscastData
import org.intellij.lang.annotations.Language

object MyConstant {
    const val ALBUM_TYPE = "album_type"
    const val PLAYLIST_TYPE = "palylist_type"
    const val SINGLE_TYPE = "single_type"
    const val ARTIST_TYPE = "artist_type"
    const val PADDING_BOTTOM_BAR = 160
    const val SONGS_SEARCH = "songs_search"
    const val ARTISTS_SEARCH = "artists_search"
    const val ALBUMS_SEARCH = "albums_search"
    const val PLAYLISTS_SEARCH = "playlists_search"
    const val VIEW_ALL_HISTORY = -91829L
    const val VIEW_ALL_LIKED = -19823L
    const val VIEW_ALL_YOUR_SONG = -749122L
    const val PLAYLIST = "Danh sách phát"
    const val YOUR = "Của bạn"
    const val FOR_YOUR = "Dành cho bạn"
    const val ARTIST = "Nghệ sĩ"
    const val MUSIC_PLAYBACK = "Music Playback"
    const val NOTIFICATION_CHANNEL_ID = "music-channel"
    const val NOTIFICATION_ID = 1
    const val CLOUNDINARY_URL = "cloudinary://546947219998971:jOd3xf4S5NUj90xEtHp-y9IFV2o@decqclrhl"

    const val IMAGE_URL =
        "https://lh3.googleusercontent.com/guPkUMfq6XoStEBVJwwWMD5dttVFgi0OXpzHZ0hvPD0kWxdVkrMbMCBNRDZlUy_N953vMI_r-6x1X_IEWQ=w544-h544-l90-rj"
    const val AVATAR_URL = "https://www.piclumen.com/wp-content/uploads/2024/10/piclumen-marquee-06.webp"
    const val NOT_AVATAR = "https://res.cloudinary.com/decqclrhl/image/upload/v1748745347/e2mp3-spotify/img/not_avatar.png"

    @Language("AGSL")
    val CUSTOM_SHADER = """
        uniform float2 resolution;
        layout(color) uniform half4 color1;
        layout(color) uniform half4 color2;
        half4 main(in float2 fragCoord) {
            float2 uv = fragCoord/resolution.xy;
            float mixValue = distance(uv, vec2(0, 1));
            return mix(color1, color2, mixValue);
        }
    """.trimIndent()

    val equalizerIconList = listOf(R.drawable.ic_24_equalizer_1,
                                   R.drawable.ic_24_equalizer_2,
                                   R.drawable.ic_24_equalizer_3,
                                   R.drawable.ic_24_equalizer_4,
                                   R.drawable.ic_24_equalizer_5,
                                   R.drawable.ic_24_equalizer_6,
                                   R.drawable.ic_24_equalizer_7)

    val genresList = listOf(R.string.genre_chill,
                            R.string.genre_hip_hop,
                            R.string.genre_energizing,
                            R.string.genre_sol,
                            R.string.genre_romantic,
                            R.string.genre_party,
                            R.string.genre_concentrate,
                            R.string.genre_blues,
                            R.string.genre_indie,
                            R.string.genre_jazz,
                            R.string.genre_lating,
                            R.string.genre_edm,
                            R.string.genre_v_pop,
                            R.string.genre_pop,
                            R.string.genre_r_and_b,
                            R.string.genre_rook)

    val sampleLibraryData =
        PrimaryLibraryData(primaryLibrary = listOf(SecondaryLibraryData(title = PLAYLIST, secondaryLibrary = listOf(YOUR, FOR_YOUR)),
                                                   SecondaryLibraryData(title = ARTIST, secondaryLibrary = emptyList())))

    val carouselThumbData =
        listOf(STFCarouselThumbData(imageUrl = IMAGE_URL, title = "Nơi này có anh", subtitle = "Sơn Tùng M-TP", description = "350M view"),
               STFCarouselThumbData(imageUrl = IMAGE_URL, title = "Nơi này có anh", subtitle = "Sơn Tùng M-TP", description = "350M view"),
               STFCarouselThumbData(imageUrl = IMAGE_URL, title = "Nơi này có anh", subtitle = "Sơn Tùng M-TP", description = "350M view"),
               STFCarouselThumbData(imageUrl = IMAGE_URL, title = "Nơi này có anh", subtitle = "Sơn Tùng M-TP", description = "350M view"),
               STFCarouselThumbData(imageUrl = IMAGE_URL, title = "Nơi này có anh", subtitle = "Sơn Tùng M-TP", description = "350M view"),
               STFCarouselThumbData(imageUrl = IMAGE_URL, title = "Nơi này có anh", subtitle = "Sơn Tùng M-TP", description = "350M view"),
               STFCarouselThumbData(imageUrl = IMAGE_URL, title = "Nơi này có anh", subtitle = "Sơn Tùng M-TP", description = "350M view"))

    val carouselHeroThumbData =
        listOf(STFCarouselHeroThumbData(imageUrl = IMAGE_URL, title = "Title", subtitle = "Description lorem ipsum solar sit anent omus"),
               STFCarouselHeroThumbData(imageUrl = IMAGE_URL, title = "Title", subtitle = "Description lorem ipsum solar sit anent omus"),
               STFCarouselHeroThumbData(imageUrl = IMAGE_URL, title = "Title", subtitle = "Description lorem ipsum solar sit anent omus"),
               STFCarouselHeroThumbData(imageUrl = IMAGE_URL, title = "Title", subtitle = "Description lorem ipsum solar sit anent omus"),
               STFCarouselHeroThumbData(imageUrl = IMAGE_URL, title = "Title", subtitle = "Description lorem ipsum solar sit anent omus"),
               STFCarouselHeroThumbData(imageUrl = IMAGE_URL, title = "Title", subtitle = "Description lorem ipsum solar sit anent omus"),
               STFCarouselHeroThumbData(imageUrl = IMAGE_URL, title = "Title", subtitle = "Description lorem ipsum solar sit anent omus"))

    val fakeSongs = listOf(SongsModel(id = 0,
                                      avatarUrl = "https://lh3.googleusercontent.com/4oHGnuOLo9RVtk-N1EH1zqcWl6LWpisMka0ije1x1Y0PgU35_L-GD8gFCYwLgRd0NpeX2ZqAMyVU-Wr_=w544-h544-l90-rj",
                                      title = "Ta Cứ Đi Cùng Nhau (cùng với Linh Cáo)",
                                      subtitle = "Đen",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 1,
                                      avatarUrl = "https://lh3.googleusercontent.com/oOqdK7E8QUm75wYoaTllb5us7bdYOJd6OlLp7wbAGU_F6kaboS-Pfz83xccnCT7GfcJycYLVJ6rDJ8A=w544-h544-l90-rj",
                                      title = "Khóa Chân (cùng với Mason Nguyen và Nam Cocain)",
                                      subtitle = "24k.Right",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 2,
                                      avatarUrl = "https://lh3.googleusercontent.com/Lmq5xKYuDuUFXjocD7V3yk_lddMWfAYvIGKHpHwRcLrShaYX0Snm53hF73qYUfoCUZZ5i8rhvAc-VzT3-A=w544-h544-l90-rj",
                                      title = "nếu lúc đó",
                                      subtitle = "tlinh và 2pillz",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 3,
                                      avatarUrl = "https://lh3.googleusercontent.com/qAJJmELH3o4sJBgx84V3LFYrT0tWcj-vThbyfMlJzby-Dm8vs3K5dPUBahTBsLscOrF_4eW41_0R28E=w544-h544-l90-rj",
                                      title = "Nếu Những Tiếc Nuối",
                                      subtitle = "Vũ.",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 4,
                                      avatarUrl = "https://lh3.googleusercontent.com/wnhSwf3pm3Bz5w4HAT-KELF7JBC92oLFgjAl4KTQO4ze46D1DGZqYgTv-UW6QuEmmeE8uBFR-EyMiP8=w544-h544-l90-rj",
                                      title = "Đừng Làm Trái Tim Anh Đau",
                                      subtitle = "Sơn Tùng M-TP",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 5,
                                      avatarUrl = "https://lh3.googleusercontent.com/WyzaRYap78pVb2_5rJDGiYv9t3bfKVFCl6fXCPpTRvigGgHdZLKuOnhX6MvFDgv82AsCFCITLdyinS8=w544-h544-l90-rj",
                                      title = "Buong Doi Tay Nhau Ra",
                                      subtitle = "Sơn Tùng M-TP",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 6,
                                      avatarUrl = "https://lh3.googleusercontent.com/IEBSWfeOBtb2SGqj7bHVD_qdRWArAU3QFIr-3AZTLXnByKuHneA0Y7DXyRty6YN4-O6J9Edg8Tq0AETm=w544-h544-l90-rj",
                                      title = "WAITING FOR YOU",
                                      subtitle = "MONO",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 1,
                                      avatarUrl = "https://lh3.googleusercontent.com/oOqdK7E8QUm75wYoaTllb5us7bdYOJd6OlLp7wbAGU_F6kaboS-Pfz83xccnCT7GfcJycYLVJ6rDJ8A=w544-h544-l90-rj",
                                      title = "Khóa Chân (cùng với Mason Nguyen và Nam Cocain)",
                                      subtitle = "24k.Right",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 2,
                                      avatarUrl = "https://lh3.googleusercontent.com/Lmq5xKYuDuUFXjocD7V3yk_lddMWfAYvIGKHpHwRcLrShaYX0Snm53hF73qYUfoCUZZ5i8rhvAc-VzT3-A=w544-h544-l90-rj",
                                      title = "nếu lúc đó",
                                      subtitle = "tlinh và 2pillz",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 3,
                                      avatarUrl = "https://lh3.googleusercontent.com/qAJJmELH3o4sJBgx84V3LFYrT0tWcj-vThbyfMlJzby-Dm8vs3K5dPUBahTBsLscOrF_4eW41_0R28E=w544-h544-l90-rj",
                                      title = "Nếu Những Tiếc Nuối",
                                      subtitle = "Vũ.",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 4,
                                      avatarUrl = "https://lh3.googleusercontent.com/wnhSwf3pm3Bz5w4HAT-KELF7JBC92oLFgjAl4KTQO4ze46D1DGZqYgTv-UW6QuEmmeE8uBFR-EyMiP8=w544-h544-l90-rj",
                                      title = "Đừng Làm Trái Tim Anh Đau",
                                      subtitle = "Sơn Tùng M-TP",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 5,
                                      avatarUrl = "https://lh3.googleusercontent.com/WyzaRYap78pVb2_5rJDGiYv9t3bfKVFCl6fXCPpTRvigGgHdZLKuOnhX6MvFDgv82AsCFCITLdyinS8=w544-h544-l90-rj",
                                      title = "Buong Doi Tay Nhau Ra",
                                      subtitle = "Sơn Tùng M-TP",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 6,
                                      avatarUrl = "https://lh3.googleusercontent.com/IEBSWfeOBtb2SGqj7bHVD_qdRWArAU3QFIr-3AZTLXnByKuHneA0Y7DXyRty6YN4-O6J9Edg8Tq0AETm=w544-h544-l90-rj",
                                      title = "WAITING FOR YOU",
                                      subtitle = "MONO",
                                      releaseDate = "24-02-2024"),
                           SongsModel(id = 7,
                                      avatarUrl = "https://lh3.googleusercontent.com/zUR9aaX2AWqeXDvKssUEBn0O1bf8JZdg8e2HyXRMwf6bBFl25LrLrbqktriYxOAmOCgrh4mk5kSDT5vd=w544-h544-l90-rj",
                                      title = "giá như",
                                      subtitle = "SOOBIN",
                                      releaseDate = "24-02-2024"))

    val fakePoscatsList = listOf(HomePoscastData(id = 0,
                                                 imageUrl = "https://i.scdn.co/image/ab67656300005f1fe12f6761d3ea413028883c7b",
                                                 namePoscast = "#1 - Yêu đơn phương.",
                                                 ep = "Tập",
                                                 owner = "MixiRadio (ft. Yuri)",
                                                 date = "3 thg 8, 2022",
                                                 time = "33min",
                                                 content = "Nói về những câu chuyện chúng ta yêu một người nào đó mà không mong đợi được sự hồi đáp từ đối phương."),
                                 HomePoscastData(id = 1,
                                                 imageUrl = "https://i.scdn.co/image/ab67656300005f1fe12f6761d3ea413028883c7b",
                                                 namePoscast = "#3 - Tết xa nhà...",
                                                 ep = "Tập",
                                                 owner = "MixiRadio (ft. Yuri)",
                                                 date = "23 thg 1, 2023",
                                                 time = "28min",
                                                 content = "Các bài hát sử dụng trong #3: 1. Tết xa- Bảo Uyên, 2. Con hứa sẽ về - Lê Bảo Bình, 3. Tết Này Con Sẽ Về - Bùi Công Nam. Cám ơn các bạn, chúc cả nhà ăn tết vui vẻ."),
                                 HomePoscastData(id = 2,
                                                 imageUrl = "https://i.scdn.co/image/ab67656300005f1f2e80911b9e6b0b2a2de631b3",
                                                 namePoscast = "#35 nếu một ngày, trái tim bạn chẳng còn rung động...",
                                                 ep = "Tập",
                                                 owner = "Thanh Alice Podcast",
                                                 date = "24 thg 2, 2025",
                                                 time = "15min",
                                                 content = "có lẽ đó là dấu hiệu của trưởng thành—khi ta không còn vỡ òa vì những điều nhỏ bé, không còn dốc lòng vào những cảm xúc nhất thời. Cho đến một ngày, ta chợt tự hỏi: Nếu đây là trưởng thành, thì vì sao trái tim mình lại im lặng đến thế?"),
                                 HomePoscastData(id = 3,
                                                 imageUrl = "https://i.scdn.co/image/ab67656300005f1faba36f4209b6d564012b2519",
                                                 namePoscast = "Nhạc Lofi 8x9x - Nhạc Xưa Chill Nhẹ Nhàng - Top Nhạc Trẻ Xưa Hot TikTok ♫ Nhạc Chill Hot TikTok 2022",
                                                 ep = "Tập",
                                                 owner = "Happy Carrot\uD83E\uDD55",
                                                 date = "18 thg 8, 2022",
                                                 time = "59min",
                                                 content = "Nhạc Lofi 8x9x - Nhạc Xưa Chill Nhẹ Nhàng - Top Nhạc Trẻ Xưa Hot TikTok ♫ Nhạc Chill Hot TikTok 2022"),
                                 HomePoscastData(id = 4,
                                                 imageUrl = "https://i.scdn.co/image/ab67656300005f1fb6b6916b24e0d6a4110f5c29",
                                                 namePoscast = "Những câu nói hay về cuộc sống hàng ngày đáng đọc nhất.",
                                                 ep = "Tập",
                                                 owner = "Nam Vo Podcast",
                                                 date = "12 thg 9, 2021",
                                                 time = "6min",
                                                 content = "Cuộc sống luôn có những cung bậc cảm xúc mà bất cứ ai trong đời cũng trãi qua từ hạnh phúc, đau khổ, vui buồn, giận hờn, yêu thương, hy vọng, thất vọng… Dưới đây là những câu nói hay về cuộc sống hàng ngày phản ánh chân thật nhất về những thứ to nhỏ trong cuộc đời của chúng ta mà chắc chắn ai cũng phải trải qua. Nếu có thời gian bạn hãy đọc và suy ngẫm những câu nói hay về cuộc sống này để hiểu rõ và giúp bạn vượt qua một cách dễ dàng hơn."),
                                 HomePoscastData(id = 5,
                                                 imageUrl = "https://i.scdn.co/image/ab67656300005f1fef34e07b7bc77987c40c04e8",
                                                 namePoscast = "#1 Tình yêu dưới góc nhìn của tâm lí học ",
                                                 ep = "Tập",
                                                 owner = "Vinvent Radio",
                                                 date = "2 thg 10, 2023",
                                                 time = "23min",
                                                 content = "Cậu có bao giờ tự hỏi chẳng hiểu sao mình với người ta rất hợp nhau nhưng cả hai chẳng dám tiến lên, hay nhiều khi cậu cảm thấy khát khao tình yêu mãnh liệt, nhưng lại không thể đồng điệu với đối phương hay không. Tình yêu dưới góc nhìn của tâm lý học sẽ giúp cậu giải đáp điều này."),
                                 HomePoscastData(id = 6,
                                                 imageUrl = "https://i.scdn.co/image/ab67656300005f1fc2c76f419f45b545f8f575c0",
                                                 namePoscast = " Neutrino - Hạt Ma Xuyên Qua Mọi Thứ Trong Vũ Trụ!!!",
                                                 ep = "Tập",
                                                 owner = "VFacts",
                                                 date = "3 thg 4, 2024",
                                                 time = "9min",
                                                 content = "#402 Neutrino - Hạt Ma Xuyên Qua Mọi Thứ Trong Vũ Trụ!!!"),
                                 HomePoscastData(id = 7,
                                                 imageUrl = "https://i.scdn.co/image/ab67656300005f1f781a575a9b1a82e095baec79",
                                                 namePoscast = "Để Ai Cần",
                                                 ep = "Tập",
                                                 owner = "Sad Listen To My Music",
                                                 date = "30 thg 9, 2023",
                                                 time = "4min",
                                                 content = "B Ray Young H"),
                                 HomePoscastData(id = 8,
                                                 imageUrl = "https://i.scdn.co/image/ab6765630000ba8af6d7432d88d5a252caa4c186",
                                                 namePoscast = "#47: tôi đứng ở rìa của ngày mai và tự hỏi giấc mơ nào sẽ tồn tại đến bình minh?",
                                                 ep = "Tập",
                                                 owner = "Thuần Podcast",
                                                 date = "21 thg 9, 2024",
                                                 time = "31min",
                                                 content = "Cảm ơn cậu đã ở đây, loveu3000"),
                                 HomePoscastData(id = 9,
                                                 imageUrl = "https://i.scdn.co/image/ab67656300005f1fe51d57039aafc5b37aa3ed5f",
                                                 namePoscast = "#23: Ai cũng có những lần đầu tiên ",
                                                 ep = "Tập",
                                                 owner = "Thỏ con lon ton - Podcast",
                                                 date = "15 thg 9, 2024",
                                                 time = "14min",
                                                 content = "Những lần đầu của cậu là gì? Đó là lần đầu cậu rung động vì một người, lần đầu cậu lên xe đi đến một nơi xa lạ, hay chỉ đơn giản là lần đầu cậu học cách yêu thương chính bản thân mình?"))

    val cardGenresSearchList = listOf(Pair(R.string.music, R.drawable.img_card_music),
                                      Pair(R.string.podcasts, R.drawable.img_card_podcast),
                                      Pair(R.string.live_events, R.drawable.img_card_live_events),
                                      Pair(R.string.made_for_you, R.drawable.img_card_mede_for_you),
                                      Pair(R.string.new_releases, R.drawable.img_card_new_releases),
                                      Pair(R.string.summer, R.drawable.img_card_summer),
                                      Pair(R.string.sanremo_festival, R.drawable.img_card_sanremo_festival),
                                      Pair(R.string.pop, R.drawable.img_card_pop),
                                      Pair(R.string.hip_hop, R.drawable.img_card_hip_hop),
                                      Pair(R.string.podcast_charts, R.drawable.img_card_podcast_charts),
                                      Pair(R.string.podcast_new_releases, R.drawable.img_card_podcast_new_releases),
                                      Pair(R.string.video_podcasts, R.drawable.img_card_video_podcast),
                                      Pair(R.string.only_on_spotify, R.drawable.img_card_only_on_spotify),
                                      Pair(R.string.charts, R.drawable.img_card_charts),
                                      Pair(R.string.latin, R.drawable.img_card_latin),
                                      Pair(R.string.dance, R.drawable.img_card_dance),
                                      Pair(R.string.rock, R.drawable.img_card_rock),
                                      Pair(R.string.indie, R.drawable.img_card_indie),
                                      Pair(R.string.mood, R.drawable.img_card_mood),
                                      Pair(R.string.party, R.drawable.img_card_party),
                                      Pair(R.string.gaming, R.drawable.img_card_gaming),
                                      Pair(R.string.chill, R.drawable.img_card_chill))

    val chipSearchInputList = listOf(R.string.artist, R.string.album, R.string.playlist, R.string.song, R.string.podcasts)
}